package com.hand33h.tulostaulu;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Chief Gear structural rules for calculators and input validation.
 * Public reference: Whiteout Survival Wiki Chief Gear page.
 * Exact set percentages intentionally remain data-driven/unset until verified by quality tier.
 */
public final class ChiefGearSetRules {
    public enum TroopType { INFANTRY, LANCER, MARKSMAN }
    public enum Slot { CAP, WATCH, COAT, PANTS, RING, WEAPON }

    public static final int UNLOCK_FURNACE_LEVEL = 22;
    public static final int CHARM_UNLOCK_FURNACE_LEVEL = 25;
    public static final int THREE_PIECE_THRESHOLD = 3;
    public static final int SIX_PIECE_THRESHOLD = 6;

    private static final Map<Slot, TroopType> SLOT_TROOP;
    static {
        EnumMap<Slot, TroopType> m = new EnumMap<>(Slot.class);
        m.put(Slot.CAP, TroopType.LANCER);
        m.put(Slot.WATCH, TroopType.LANCER);
        m.put(Slot.COAT, TroopType.INFANTRY);
        m.put(Slot.PANTS, TroopType.INFANTRY);
        m.put(Slot.RING, TroopType.MARKSMAN);
        m.put(Slot.WEAPON, TroopType.MARKSMAN);
        SLOT_TROOP = Collections.unmodifiableMap(m);
    }

    public static TroopType troopFor(Slot slot) { return SLOT_TROOP.get(slot); }

    /** Three matching-quality pieces unlock the all-troop Defense set effect. */
    public static boolean hasDefenseSetBonus(int matchingQualityPieces) {
        return matchingQualityPieces >= THREE_PIECE_THRESHOLD;
    }

    /** Six matching-quality pieces unlock the all-troop Attack set effect. */
    public static boolean hasAttackSetBonus(int matchingQualityPieces) {
        return matchingQualityPieces >= SIX_PIECE_THRESHOLD;
    }

    /**
     * Deliberately no guessed percentage here. The magnitude rises with gear quality and
     * must come from a verified tier table/in-game preview before simulation uses it.
     */
    public static boolean exactSetPercentageRequiresVerifiedTierData() { return true; }

    public static final String SOURCE_URL = "https://www.whiteoutsurvival.wiki/chief-gear/chief-gear/";
    private ChiefGearSetRules() {}
}
