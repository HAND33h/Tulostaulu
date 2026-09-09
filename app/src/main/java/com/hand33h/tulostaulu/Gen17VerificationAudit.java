package com.hand33h.tulostaulu;

/** Source-backed Gen17 verification notes. Keep uncertain public data out of automatic calibration. */
public final class Gen17VerificationAudit {
    public static final String LAST_CROSS_CHECK = "2026-09-09";

    /**
     * Public sources confirm Bertha Antibodies increases Defender Troops' Lethality to 15% at Lv5,
     * but the complete Lv1..Lv5 widget ladder was not independently published by the sources checked.
     */
    public static final boolean BERTHA_ANTIBODIES_FULL_LADDER_VERIFIED = false;
    public static final double BERTHA_ANTIBODIES_CONFIRMED_LV5 = 15.0;
    public static final String BERTHA_ANTIBODIES_STATUS = "PARTIALLY_VERIFIED_LV5_ONLY";
    public static final String BERTHA_ANTIBODIES_PROVENANCE =
            "Whiteout Survival Wiki / wos-wiki.de / WSCO; cross-checked 2026-09-09";
    public static final String BERTHA_ANTIBODIES_NOTE =
            "Do not auto-calibrate intermediate widget levels from an inferred 5/7.5/10/12.5/15 pattern until independently sourced.";

    private Gen17VerificationAudit() {}
}
