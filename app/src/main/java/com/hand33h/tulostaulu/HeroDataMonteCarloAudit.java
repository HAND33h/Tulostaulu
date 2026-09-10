package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Deterministic 2,000-round Monte Carlo preflight for verified hero skill data.
 *
 * The audit never invents missing WOS values. It samples already source-checked rows,
 * validates numeric sanity and provenance, and keeps explicit SOURCE_CONFLICT rows out
 * of automatic simulation. Seed is fixed so the same repository state reproduces the
 * same audit result.
 */
public final class HeroDataMonteCarloAudit {
    public static final int ITERATIONS = 2000;
    public static final long SEED = 1674_2000L;

    public static final class Result {
        public final int iterations;
        public final int rowsAvailable;
        public final int passed;
        public final int warnings;
        public final int failed;
        public final String summary;

        Result(int iterations, int rowsAvailable, int passed, int warnings, int failed, String summary) {
            this.iterations = iterations;
            this.rowsAvailable = rowsAvailable;
            this.passed = passed;
            this.warnings = warnings;
            this.failed = failed;
            this.summary = summary;
        }

        public boolean isClean() { return failed == 0; }
    }

    private static final class Entry {
        final String hero, skill, effect, target, trigger, source;
        final double[] values;
        Entry(String hero, String skill, String effect, String target, String trigger, double[] values, String source) {
            this.hero = hero; this.skill = skill; this.effect = effect; this.target = target;
            this.trigger = trigger; this.values = values; this.source = source;
        }
    }

    public static Result run2000() {
        List<Entry> rows = allRows();
        if (rows.isEmpty()) return new Result(ITERATIONS, 0, 0, 0, ITERATIONS, "No verified hero rows available");

        Random random = new Random(SEED);
        int passed = 0, warnings = 0, failed = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            Entry row = rows.get(random.nextInt(rows.size()));
            int state = validate(row);
            if (state > 0) passed++;
            else if (state == 0) warnings++;
            else failed++;
        }

        String summary = "Monte Carlo hero-data audit: " + ITERATIONS + " rounds, " + rows.size()
                + " source-checked rows, passed=" + passed + ", warnings=" + warnings + ", failed=" + failed;
        return new Result(ITERATIONS, rows.size(), passed, warnings, failed, summary);
    }

    /** 1=usable, 0=explicit conflict/partial ladder, -1=invalid. */
    private static int validate(Entry r) {
        if (blank(r.hero) || blank(r.skill) || blank(r.effect) || blank(r.target) || blank(r.trigger) || blank(r.source)) return -1;
        if (r.values == null || r.values.length == 0) return -1;
        for (double value : r.values) if (Double.isNaN(value) || Double.isInfinite(value) || value < 0) return -1;

        String source = r.source.toLowerCase();
        if (!(source.contains("cross-checked") || source.contains("updated 2026-") || source.contains("audited 2026-"))) return -1;

        if ("SOURCE_CONFLICT".equals(r.effect) || "EXPEDITION_DO_NOT_AUTO_SIMULATE".equals(r.trigger)) return 0;
        if (r.values.length != 5) return 0;

        if ("ATTACKS_REQUIRED".equals(r.effect)) {
            for (int i = 1; i < r.values.length; i++) if (r.values[i] > r.values[i - 1]) return -1;
        }
        return 1;
    }

    private static boolean blank(String s) { return s == null || s.trim().isEmpty(); }

    private static List<Entry> allRows() {
        List<Entry> out = new ArrayList<>();
        for (VerifiedHeroSkillGen0.Row r : VerifiedHeroSkillGen0.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        // Gina is currently maintained as a standalone Gen0 integration source; include it
        // explicitly so new/current Gina values cannot bypass the 2,000-round data audit.
        for (VerifiedHeroSkillGina.Row r : VerifiedHeroSkillGina.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen1.Row r : VerifiedHeroSkillGen1.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen2.Row r : VerifiedHeroSkillGen2.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen3.Row r : VerifiedHeroSkillGen3.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen4.Row r : VerifiedHeroSkillGen4.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen5.Row r : VerifiedHeroSkillGen5.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen6.Row r : VerifiedHeroSkillGen6.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen7.Row r : VerifiedHeroSkillGen7.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen8.Row r : VerifiedHeroSkillGen8.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        for (VerifiedHeroSkillGen9.Row r : VerifiedHeroSkillGen9.DATA) out.add(new Entry(r.hero,r.skill,r.effect,r.target,r.trigger,r.values,r.source));
        return out;
    }

    private HeroDataMonteCarloAudit() {}
}
