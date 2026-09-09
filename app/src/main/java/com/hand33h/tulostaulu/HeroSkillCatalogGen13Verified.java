package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cross-verified Generation 13 Expedition data.
 *
 * Primary source: https://wosheroes.com/heroes/generation-13-heroes/
 * Cross-checks:
 * - https://www.wosbattlemaster.com/heroes/gisela
 * - https://www.wosbattlemaster.com/heroes/vulcanus
 * - https://wostools.net/wiki/heroes/flora
 * - https://wostools.net/wiki/heroes/vulcanus
 *
 * This class intentionally stores only public values that were independently
 * corroborated. No inferred or interpolated values are included.
 */
public final class HeroSkillCatalogGen13Verified {

    public static final class Effect {
        public final String hero;
        public final String skill;
        public final int expeditionOrder;
        public final String effect;
        public final String target;
        public final String trigger;
        public final double[] values;
        public final String sourceUrl;
        public final String sourceNote;

        Effect(String hero, String skill, int expeditionOrder, String effect,
               String target, String trigger, double[] values,
               String sourceUrl, String sourceNote) {
            this.hero = hero;
            this.skill = skill;
            this.expeditionOrder = expeditionOrder;
            this.effect = effect;
            this.target = target;
            this.trigger = trigger;
            this.values = values;
            this.sourceUrl = sourceUrl;
            this.sourceNote = sourceNote;
        }
    }

    private static final String PRIMARY =
            "https://wosheroes.com/heroes/generation-13-heroes/";

    private static final List<Effect> DATA;

    static {
        ArrayList<Effect> d = new ArrayList<>();

        // Gisela — cross-checked with Whiteout Battlemaster and WoSTools.
        add(d, "Gisela", "Alloyed Defense", 1,
                "TROOP_DEFENSE_UP", "INFANTRY", "PASSIVE",
                v(6, 12, 18, 24, 30),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Gisela", "Scavengeworks", 2,
                "TROOP_DEFENSE_UP", "ALL_TROOPS", "ON_INFANTRY_ATTACK_40_PERCENT_1_TURN",
                v(10, 20, 30, 40, 50),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Gisela", "Trial Shield", 3,
                "DAMAGE_TAKEN_DOWN", "ALL_TROOPS", "PROC_40_PERCENT",
                v(10, 20, 30, 40, 50),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Gisela", "Auto-Target", 0,
                "DEFENDER_ATTACK_UP", "DEFENDER_TROOPS", "WIDGET_EXPEDITION",
                v(5, 7.5, 10, 12.5, 15),
                "Exclusive Expedition skill; cross-verified public ladder");

        // Flora — cross-checked with WoSTools and Whiteout Survival Wiki.
        add(d, "Flora", "Enmiring Vines", 1,
                "ENEMY_DAMAGE_TAKEN_UP", "ENEMY_ALL", "PROC_50_PERCENT",
                v(10, 20, 30, 40, 50),
                "Cross-verified: WOS Heroes + WoSTools + Whiteout Survival Wiki");
        add(d, "Flora", "Plantage Infantry", 2,
                "DAMAGE_TAKEN_DOWN", "INFANTRY", "PASSIVE",
                v(5, 10, 15, 20, 25),
                "Cross-verified: WOS Heroes + WoSTools + Whiteout Survival Wiki");
        add(d, "Flora", "Plantage Lancer", 2,
                "DAMAGE_DEALT_UP", "LANCER", "PASSIVE",
                v(5, 10, 15, 20, 25),
                "Cross-verified: WOS Heroes + WoSTools + Whiteout Survival Wiki");
        add(d, "Flora", "Confusion Pollen Infantry", 3,
                "ENEMY_DAMAGE_TAKEN_UP", "ENEMY_INFANTRY", "EVERY_4_TURNS_2_TURNS",
                v(6, 12, 18, 24, 30),
                "Cross-verified: WOS Heroes + WoSTools + Whiteout Survival Wiki");
        add(d, "Flora", "Confusion Pollen Marksman", 3,
                "ENEMY_DAMAGE_DEALT_DOWN", "ENEMY_MARKSMAN", "EVERY_4_TURNS_2_TURNS",
                v(6, 12, 18, 24, 30),
                "Cross-verified: WOS Heroes + WoSTools + Whiteout Survival Wiki");
        add(d, "Flora", "Fruit of Life", 0,
                "DEFENDER_HEALTH_UP", "DEFENDER_TROOPS", "WIDGET_EXPEDITION",
                v(5, 7.5, 10, 12.5, 15),
                "Exclusive Expedition skill; cross-verified public ladder");

        // Vulcanus — cross-checked with Whiteout Battlemaster and WoSTools.
        add(d, "Vulcanus", "Raging Storm", 1,
                "ENEMY_ATTACK_DOWN", "ENEMY_ALL", "PASSIVE",
                v(4, 8, 12, 16, 20),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Vulcanus", "Breaker Steel Damage", 2,
                "EXTRA_DAMAGE", "ALL_TROOPS", "EVERY_5_ATTACKS",
                v(20, 40, 60, 80, 100),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Vulcanus", "Breaker Steel Debuff", 2,
                "ENEMY_DAMAGE_TAKEN_UP", "TARGET", "AFTER_EVERY_5_ATTACKS_NEXT_HIT",
                v(5, 7.5, 10, 12.5, 15),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Vulcanus", "True Strike Defense", 3,
                "ENEMY_DEFENSE_DOWN", "ENEMY_INFANTRY_LANCER", "EVERY_3_TURNS_3_TURNS",
                v(12, 24, 36, 48, 60),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Vulcanus", "True Strike Marksman", 3,
                "MARKSMAN_ATTACK_UP", "MARKSMAN", "EVERY_3_TURNS_1_TURN",
                v(12, 24, 36, 48, 60),
                "Cross-verified: WOS Heroes + Whiteout Battlemaster + WoSTools");
        add(d, "Vulcanus", "Born King", 0,
                "RALLY_ATTACK_UP", "RALLY_TROOPS", "WIDGET_EXPEDITION",
                v(5, 7.5, 10, 12.5, 15),
                "Exclusive Expedition skill; cross-verified public ladder");

        DATA = Collections.unmodifiableList(d);
    }

    private static void add(List<Effect> out, String hero, String skill,
                            int expeditionOrder, String effect, String target,
                            String trigger, double[] values, String note) {
        out.add(new Effect(hero, skill, expeditionOrder, effect, target, trigger,
                values, PRIMARY, note));
    }

    private static double[] v(double... values) {
        return values;
    }

    public static List<Effect> all() {
        return DATA;
    }

    public static List<Effect> forHero(String hero) {
        if (hero == null) return Collections.emptyList();
        ArrayList<Effect> out = new ArrayList<>();
        for (Effect e : DATA) {
            if (e.hero.equalsIgnoreCase(hero)) out.add(e);
        }
        return Collections.unmodifiableList(out);
    }

    public static Effect primaryExpeditionSkill(String hero) {
        if (hero == null) return null;
        for (Effect e : DATA) {
            if (e.expeditionOrder == 1 && e.hero.equalsIgnoreCase(hero)) return e;
        }
        return null;
    }

    private HeroSkillCatalogGen13Verified() {}
}
