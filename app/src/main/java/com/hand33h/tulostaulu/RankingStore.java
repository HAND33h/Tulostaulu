package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Session-local OCR rankings, isolated by state and ranking category. */
public final class RankingStore {
    private RankingStore() {}
    public static final String[] SHEETS={"Alliance Power","Personal Power","Furnace Level","Hero Power","Total Hero Power","Hero Gear Power","Building Power","Research Power","Chief Gear Power","Chief Charm Power","Total Pet Power","Island Prosperity","The Labyrinth","Expert Power"};
    private static final Map<String,Map<String,List<String[]>>> DATA=new LinkedHashMap<>();

    public static synchronized void addRows(String state,String sheet,List<String[]> rows) {
        if(!StateSelection.valid(state) || rows==null) return;
        boolean known=false; for(String name:SHEETS) if(name.equals(sheet)) known=true;
        if(!known) return;
        Map<String,List<String[]>> sheets=DATA.computeIfAbsent(state.trim(),key->new LinkedHashMap<>());
        List<String[]> target=sheets.computeIfAbsent(sheet,key->new ArrayList<>());
        for(String[] row:rows) {
            if(row==null || row.length<("Alliance Power".equals(sheet)?3:5)) continue;
            String key=rowKey(sheet,row); if(key.isEmpty()) continue;
            int index=-1;
            for(int i=0;i<target.size();i++) if(rowKey(sheet,target.get(i)).equals(key)){index=i;break;}
            if(index<0) target.add(row.clone()); else target.set(index,row.clone());
        }
        target.sort((a,b)->Integer.compare(rankOf(a),rankOf(b)));
        if(target.size()>100) target.subList(100,target.size()).clear();
    }

    public static synchronized List<String[]> getRows(String state,String sheet) {
        List<String[]> out=new ArrayList<>();
        Map<String,List<String[]>> sheets=state==null?null:DATA.get(state.trim());
        List<String[]> rows=sheets==null?null:sheets.get(sheet);
        if(rows!=null) for(String[] row:rows) out.add(row.clone());
        return out;
    }
    public static synchronized void clear(){DATA.clear();}
    private static int rankOf(String[] row){try{return Integer.parseInt(row[0]);}catch(Exception e){return Integer.MAX_VALUE;}}
    private static String rowKey(String sheet,String[] row){
        String name=row[1]==null?"":row[1].trim().toLowerCase(Locale.ROOT);
        if("Alliance Power".equals(sheet)) return name.isEmpty()?"":"name:"+name;
        String fid=row[2]==null?"":row[2].trim();
        return !fid.isEmpty()?"fid:"+fid:(name.isEmpty()?"":"name:"+name);
    }
}
