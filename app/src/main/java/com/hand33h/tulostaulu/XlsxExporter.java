package com.hand33h.tulostaulu;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** Minimal dependency-free XLSX writer for the 14-sheet WOS ranking workbook. */
public final class XlsxExporter {
    private XlsxExporter() {}

    private static final String[] SHEETS = {
            "Alliance Power", "Personal Power", "Furnace Level", "Hero Power",
            "Total Hero Power", "Hero Gear Power", "Building Power", "Research Power",
            "Chief Gear Power", "Chief Charm Power", "Total Pet Power", "Island Prosperity",
            "The Labyrinth", "Expert Power"
    };

    private static final String[][] HEADERS = {
            {"Rank", "Alliance Name", "Power"},
            {"Rank", "Player", "id", "Personal Power", "Alliance"},
            {"Rank", "Player", "id", "Furnace Lv", "Alliance"},
            {"Rank", "Player", "id", "Hero Power", "Alliance"},
            {"Rank", "Player", "id", "Total Hero Power", "Alliance"},
            {"Rank", "Player", "id", "Hero Gear Power", "Alliance"},
            {"Rank", "Player", "id", "Building Power", "Alliance"},
            {"Rank", "Player", "id", "Research Power", "Alliance"},
            {"Rank", "Player", "id", "Chief Gear Power", "Alliance"},
            {"Rank", "Player", "id", "Chief Charm Power", "Alliance"},
            {"Rank", "Player", "id", "Total Pet Power", "Alliance"},
            {"Rank", "Player", "id", "Island Prosperity", "Alliance"},
            {"Rank", "Player", "id", "Labyrinth Score", "Alliance"},
            {"Rank", "Player", "id", "Expert Power", "Alliance"}
    };

    /**
     * personalRows: rank, player, id, personalPower, alliance.
     * Other tabs are created with the exact template headers and remain empty until
     * the public API exposes those state ranking datasets.
     */
    public static void write(OutputStream output, List<String[]> personalRows) throws Exception {
        try (ZipOutputStream zip = new ZipOutputStream(output, StandardCharsets.UTF_8)) {
            put(zip, "[Content_Types].xml", contentTypes());
            put(zip, "_rels/.rels", rootRels());
            put(zip, "docProps/app.xml", appProps());
            put(zip, "docProps/core.xml", coreProps());
            put(zip, "xl/workbook.xml", workbook());
            put(zip, "xl/_rels/workbook.xml.rels", workbookRels());
            put(zip, "xl/styles.xml", styles());
            for (int i = 0; i < SHEETS.length; i++) {
                List<String[]> data = i == 1 ? personalRows : null;
                put(zip, "xl/worksheets/sheet" + (i + 1) + ".xml", sheetXml(HEADERS[i], data));
            }
        }
    }

    private static void put(ZipOutputStream zip, String name, String text) throws Exception {
        zip.putNextEntry(new ZipEntry(name));
        zip.write(text.getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();
    }

    private static String contentTypes() {
        StringBuilder s = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\"><Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/><Default Extension=\"xml\" ContentType=\"application/xml\"/><Override PartName=\"/xl/workbook.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\"/><Override PartName=\"/xl/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml\"/><Override PartName=\"/docProps/core.xml\" ContentType=\"application/vnd.openxmlformats-package.core-properties+xml\"/><Override PartName=\"/docProps/app.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.extended-properties+xml\"/>");
        for (int i = 1; i <= SHEETS.length; i++) s.append("<Override PartName=\"/xl/worksheets/sheet").append(i).append(".xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\"/>");
        return s.append("</Types>").toString();
    }

    private static String rootRels() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/><Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties\" Target=\"docProps/core.xml\"/><Relationship Id=\"rId3\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties\" Target=\"docProps/app.xml\"/></Relationships>";
    }

    private static String workbook() {
        StringBuilder s = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><workbook xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"><sheets>");
        for (int i = 0; i < SHEETS.length; i++) s.append("<sheet name=\"").append(xml(SHEETS[i])).append("\" sheetId=\"").append(i + 1).append("\" r:id=\"rId").append(i + 1).append("\"/>");
        return s.append("</sheets></workbook>").toString();
    }

    private static String workbookRels() {
        StringBuilder s = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">");
        for (int i = 1; i <= SHEETS.length; i++) s.append("<Relationship Id=\"rId").append(i).append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" Target=\"worksheets/sheet").append(i).append(".xml\"/>");
        s.append("<Relationship Id=\"rId").append(SHEETS.length + 1).append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/></Relationships>");
        return s.toString();
    }

    private static String styles() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><styleSheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\"><fonts count=\"2\"><font><sz val=\"11\"/><name val=\"Calibri\"/></font><font><b/><color rgb=\"FFFFFFFF\"/><sz val=\"11\"/><name val=\"Calibri\"/></font></fonts><fills count=\"3\"><fill><patternFill patternType=\"none\"/></fill><fill><patternFill patternType=\"gray125\"/></fill><fill><patternFill patternType=\"solid\"><fgColor rgb=\"FF1F4E78\"/><bgColor indexed=\"64\"/></patternFill></fill></fills><borders count=\"1\"><border><left/><right/><top/><bottom/><diagonal/></border></borders><cellStyleXfs count=\"1\"><xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\"/></cellStyleXfs><cellXfs count=\"2\"><xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\"/><xf numFmtId=\"0\" fontId=\"1\" fillId=\"2\" borderId=\"0\" xfId=\"0\" applyFont=\"1\" applyFill=\"1\" applyAlignment=\"1\"><alignment horizontal=\"center\"/></xf></cellXfs><cellStyles count=\"1\"><cellStyle name=\"Normal\" xfId=\"0\" builtinId=\"0\"/></cellStyles></styleSheet>";
    }

    private static String sheetXml(String[] headers, List<String[]> rows) {
        int cols = headers.length;
        int count = rows == null ? 0 : rows.size();
        String last = col(cols) + (count + 1);
        StringBuilder s = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\"><dimension ref=\"A1:").append(last).append("\"/><sheetViews><sheetView workbookViewId=\"0\"><pane ySplit=\"1\" topLeftCell=\"A2\" activePane=\"bottomLeft\" state=\"frozen\"/></sheetView></sheetViews><cols>");
        for (int c = 1; c <= cols; c++) {
            double width = c == 2 ? 28 : (c == cols && cols == 5 ? 24 : 16);
            s.append("<col min=\"").append(c).append("\" max=\"").append(c).append("\" width=\"").append(width).append("\" customWidth=\"1\"/>");
        }
        s.append("</cols><sheetData><row r=\"1\">");
        for (int c = 0; c < headers.length; c++) s.append(textCell(col(c + 1) + "1", headers[c], true));
        s.append("</row>");
        if (rows != null) {
            int r = 2;
            for (String[] row : rows) {
                s.append("<row r=\"").append(r).append("\">");
                for (int c = 0; c < headers.length; c++) {
                    String v = c < row.length && row[c] != null ? row[c] : "";
                    boolean numeric = (c == 0 || c == 2 || c == 3) && v.matches("-?\\d+(\\.\\d+)?");
                    s.append(numeric ? numberCell(col(c + 1) + r, v) : textCell(col(c + 1) + r, v, false));
                }
                s.append("</row>");
                r++;
            }
        }
        return s.append("</sheetData><autoFilter ref=\"A1:").append(col(cols)).append(Math.max(1, count + 1)).append("\"/></worksheet>").toString();
    }

    private static String textCell(String ref, String value, boolean header) {
        return "<c r=\"" + ref + "\" t=\"inlineStr\"" + (header ? " s=\"1\"" : "") + "><is><t xml:space=\"preserve\">" + xml(value) + "</t></is></c>";
    }

    private static String numberCell(String ref, String value) {
        return "<c r=\"" + ref + "\"><v>" + value + "</v></c>";
    }

    private static String col(int n) {
        StringBuilder s = new StringBuilder();
        while (n > 0) { n--; s.insert(0, (char)('A' + (n % 26))); n /= 26; }
        return s.toString();
    }

    private static String xml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
    }

    private static String appProps() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\" xmlns:vt=\"http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes\"><Application>WOS Tulostaulu</Application></Properties>";
    }

    private static String coreProps() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp:coreProperties xmlns:cp=\"http://schemas.openxmlformats.org/package/2006/metadata/core-properties\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><dc:creator>HAND33h</dc:creator><dc:title>WOS State Ranking Database</dc:title></cp:coreProperties>";
    }
}
