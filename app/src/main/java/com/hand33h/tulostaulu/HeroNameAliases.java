package com.hand33h.tulostaulu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Canonical hero-name compatibility for renamed Whiteout Survival heroes.
 *
 * Public-source verification (2026-09-10):
 * - Walis Bokan was renamed to Lumak Bokan; current WOS Forge / current community wiki use Lumak Bokan,
 *   while older databases still expose Walis Bokan.
 * - Ling Shuang was renamed/reworked to Ling Xue; current WOS Forge / current community wiki use Ling Xue,
 *   while older databases and game-version records expose Ling Shuang.
 *
 * Keep aliases here so imports, screenshots, API/Excel exports and simulator lookups using legacy names
 * resolve to the same current hero instead of creating duplicate records.
 */
public final class HeroNameAliases {
    private static final Map<String, String> ALIASES;

    static {
        Map<String, String> m = new HashMap<>();
        m.put(key("Walis Bokan"), "Lumak Bokan");
        m.put(key("Lumak Bokan"), "Lumak Bokan");
        m.put(key("Ling Shuang"), "Ling Xue");
        m.put(key("Ling Xue"), "Ling Xue");
        ALIASES = Collections.unmodifiableMap(m);
    }

    public static String canonicalize(String heroName) {
        if (heroName == null) return null;
        String trimmed = heroName.trim();
        String canonical = ALIASES.get(key(trimmed));
        return canonical != null ? canonical : trimmed;
    }

    public static boolean isKnownAlias(String heroName) {
        return heroName != null && ALIASES.containsKey(key(heroName));
    }

    private static String key(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private HeroNameAliases() {}
}
