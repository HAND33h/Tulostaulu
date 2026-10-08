import com.hand33h.tulostaulu.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.xml.parsers.*;
public class RankingExportTest {
    private static int checks;
    private static void check(boolean ok){checks++;if(!ok)throw new AssertionError("Check "+checks);}
    public static void main(String[] args)throws Exception {
        RankingStore.clear();
        check(StateSelection.valid(" 1738 "));
        for(String bad:new String[]{"", "0", "-1", "1.5", "State 77", "01"})check(!StateSelection.valid(bad));
        RankingStore.addRows("77","Personal Power",Collections.singletonList(new String[]{"2","Old name","00123456","100","SCA"}));
        RankingStore.addRows("77","Personal Power",Collections.singletonList(new String[]{"1","New name","00123456","200","SCA"}));
        RankingStore.addRows("1674","Personal Power",Collections.singletonList(new String[]{"1","Other state","99999999","999","AoA"}));
        check(RankingStore.getRows("77","Personal Power").size()==1);
        check(RankingStore.getRows("77","Personal Power").get(0)[3].equals("200"));
        String[] copy=RankingStore.getRows("77","Personal Power").get(0);copy[1]="mutation";
        check(RankingStore.getRows("77","Personal Power").get(0)[1].equals("New name"));
        check(RankingStore.getRows("1738","Personal Power").isEmpty());
        RankingStore.addRows("77","Alliance Power",Collections.singletonList(new String[]{"2","SCA","100"}));
        RankingStore.addRows("77","Alliance Power",Collections.singletonList(new String[]{"1","SCA","300"}));
        check(RankingStore.getRows("77","Alliance Power").size()==1);
        check(RankingStore.getRows("77","Alliance Power").get(0)[2].equals("300"));
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        XlsxExporter.write(bytes,"77",Collections.singletonList(new String[]{"1","A&B\u0001","00123456","200","SCA"}));
        int sheets=0;String personal="";
        try(ZipInputStream zip=new ZipInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            for(ZipEntry e;(e=zip.getNextEntry())!=null;) {
                byte[] xml=zip.readAllBytes();
                if(e.getName().endsWith(".xml"))DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml));
                if(e.getName().startsWith("xl/worksheets/"))sheets++;
                if(e.getName().equals("xl/worksheets/sheet2.xml"))personal=new String(xml,java.nio.charset.StandardCharsets.UTF_8);
            }
        }
        check(sheets==14);
        check(personal.contains("r=\"C2\" t=\"inlineStr\""));
        check(personal.contains("00123456"));
        check(personal.contains("A&amp;B"));
        check(!personal.contains("Other state"));
        try {XlsxExporter.write(new ByteArrayOutputStream(),"",Collections.emptyList());throw new AssertionError();}catch(IllegalArgumentException expected){checks++;}
        System.out.println(checks+" ranking/export checks passed");
    }
}
