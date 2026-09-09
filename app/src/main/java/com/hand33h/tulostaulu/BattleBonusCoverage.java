package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Official battlefield bonus coverage checklist.
 * Source: Century Games Whiteout Survival Help Center, checked 2026-09-09.
 * This deliberately records inclusion only; it does not invent formulas for systems
 * whose exact public numeric interaction is not documented.
 */
public final class BattleBonusCoverage {
    private BattleBonusCoverage() {}

    public static final String SOURCE_URL = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9143-which-bonuses-take-effect-on-the-battlefield/";

    public static final List<String> OFFICIAL_BATTLEFIELD_INPUTS = Collections.unmodifiableList(Arrays.asList(
        "Troops", "Hero Levels", "Hero Star Levels", "Hero Skills", "Hero Gear",
        "Hero Exclusive Gear stats", "Jeronimo global bonus", "Natalia global bonus",
        "Pet Skills", "Pet Stats", "Pet Levels", "Pet Advancements", "Pet Refinements",
        "Chief Charms", "Chief Gear", "Tech", "Fire Crystal Tech", "Flame Tech",
        "Alliance Tech", "Daybreak Island", "Skins", "VIP Levels", "Buildings",
        "Facility", "Expert Level", "Expert Relationship Advancements",
        "Expert Research", "Expert Skills"
    ));

    public static boolean petSkillManualActivationStacks(){return false;}
    public static boolean facilityMustBeActiveDuringSquadSetup(){return true;}
    public static boolean jeronimoGlobalAppliesWhenNotDeployed(){return true;}
    public static boolean nataliaGlobalAppliesWhenNotDeployed(){return true;}

    /** Hero skill bonuses are calculated in battle and are not displayed as report stats. */
    public static boolean heroSkillBonusShownInBattleReport(){return false;}

    public static boolean isOfficialBattlefieldInput(String name){
        for(String v:OFFICIAL_BATTLEFIELD_INPUTS) if(v.equalsIgnoreCase(name)) return true;
        return false;
    }
}
