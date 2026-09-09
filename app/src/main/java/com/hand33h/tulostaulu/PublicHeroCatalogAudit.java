package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Runtime audit for curated Expedition data. Prevents incomplete/ambiguous rows being treated as complete. */
public final class PublicHeroCatalogAudit {
    public static final int EXPECTED_PUBLIC_EXPEDITION_SKILLS = 167;
    public static final int EXPECTED_PUBLIC_HEROES = 67;
    public static final String VERIFICATION_STATE = "PARTIALLY_VERIFIED";
    public static final String LAST_PUBLIC_CROSS_CHECK = "2026-09-09";

    public static final class Report {
        public final int rows;
        public final int heroes;
        public final List<String> warnings;
        Report(int rows, int heroes, List<String> warnings) {
            this.rows = rows; this.heroes = heroes; this.warnings = warnings;
        }
        public boolean isCompleteAgainstPublicIndex() {
            return heroes >= EXPECTED_PUBLIC_HEROES && rows >= EXPECTED_PUBLIC_EXPEDITION_SKILLS && warnings.isEmpty();
        }
    }

    public static Report audit() {
        List<PublicHeroSkillCatalog.Skill> all = PublicHeroSkillCatalog.all();
        Set<String> heroes = new HashSet<>();
        Map<String,Integer> rowsByHero = new HashMap<>();
        ArrayList<String> warnings = new ArrayList<>();
        for (PublicHeroSkillCatalog.Skill s : all) {
            heroes.add(s.hero);
            rowsByHero.put(s.hero, rowsByHero.containsKey(s.hero) ? rowsByHero.get(s.hero) + 1 : 1);
            if (s.values == null || s.values.length == 0) warnings.add(s.hero + ": " + s.name + " has no values");
            if (s.values != null && s.values.length != 5) warnings.add(s.hero + ": " + s.name + " is not a Lv1-Lv5 ladder");
            if (s.source == null || s.source.trim().isEmpty()) warnings.add(s.hero + ": " + s.name + " has no provenance");
        }
        if (heroes.size() < EXPECTED_PUBLIC_HEROES) warnings.add("Hero coverage " + heroes.size() + "/" + EXPECTED_PUBLIC_HEROES);
        if (all.size() < EXPECTED_PUBLIC_EXPEDITION_SKILLS) warnings.add("Row coverage " + all.size() + "/" + EXPECTED_PUBLIC_EXPEDITION_SKILLS);
        // Repository-wide public-data verification is not complete. Do not treat file/class names containing
        // 'Verified' as proof that every value has been independently cross-checked.
        warnings.add("AUDIT_STATE " + VERIFICATION_STATE + ": repository-wide hero/battle/progression data has not yet been independently cross-checked row-by-row; only explicitly sourced/cross-checked rows may be treated as verified.");
        // Known source conflict: WhiteoutData Gen2 description says Defender Attack +15%, preview labels Defense Up.
        warnings.add("SOURCE_CONFLICT Flint/Dragonbreath: Defender Attack description vs Defense upgrade-preview label; do not auto-calibrate from this row.");
        return new Report(all.size(), heroes.size(), warnings);
    }

    private PublicHeroCatalogAudit() {}
}
