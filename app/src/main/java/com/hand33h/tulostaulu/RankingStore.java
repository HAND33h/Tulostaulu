package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** In-memory store for ranking rows imported from screenshots during the current app session. */
public final class RankingStore {
    private RankingStore() {}

    public static final String[] SHEETS = {
            "Alliance Power", "Personal Power", "Furnace Level", "Hero Power",
            "Total Hero Power", "Hero Gear Power", "Building Power", "Research Power",
            "Chief Gear Power", "Chief Charm Power", "Total Pet Power", "Island Prosperity",
            "The Labyrinth", "Expert Power"
    };

    private static final Map<String, List<String[]>> DATA = new LinkedHashMap<>();

    static {
        for (String sheet : SHEETS) DATA.put(sheet, new ArrayList<>());
    }

    public static synchronized void addRows(String sheet, List<String[]> rows) {
        if (sheet == null || rows == null || rows.isEmpty()) return;
        List<String[]> target = DATA.get(sheet);
        if (target == null) return;
        for (String[] row : rows) {
            if (row == null) continue;
            String key = rowKey(row);
            boolean exists = false;
            for (String[] old : target) {
                if (rowKey(old).equals(key)) { exists = true; break; }
            }
            if (!exists) target.add(row.clone());
        }
        Collections.sort(target, (a,b) -> Integer.compare(rankOf(a), rankOf(b)));
        if (target.size() > 100) target.subList(100, target.size()).clear();
    }

    public static synchronized List<String[]> getRows(String sheet) {
        List<String[]> src = DATA.get(sheet);
        List<String[]> out = new ArrayList<>();
        if (src != null) for (String[] row : src) out.add(row.clone());
        return out;
    }

    public static synchronized int totalRows() {
        int n = 0;
        for (List<String[]> rows : DATA.values()) n += rows.size();
        return n;
    }

    public static synchronized void clear() {
        for (List<String[]> rows : DATA.values()) rows.clear();
    }

    private static int rankOf(String[] row) {
        if (row == null || row.length == 0) return 9999;
        try { return Integer.parseInt(row[0].replaceAll("[^0-9]", "")); }
        catch (Exception e) { return 9999; }
    }

    private static String rowKey(String[] row) {
        String id = row.length > 2 ? row[2] : "";
        String name = row.length > 1 ? row[1] : "";
        String value = row.length > 3 ? row[3] : (row.length > 2 ? row[2] : "");
        return (id + "|" + name + "|" + value).toLowerCase();
    }
}
