package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Central registry for structured WOS data used by Tulostaulu calculators.
 *
 * Design rule: calculators must consume normalized internal data. External/community
 * sources are provenance and update inputs only; the UI must not depend on opening
 * third-party calculator links.
 *
 * Priority: OFFICIAL > STRUCTURED_COMMUNITY > COMMUNITY_REFERENCE.
 * Community values must be validated before being marked safe for automatic scoring.
 */
public final class WosDataSourceRegistry {
    private WosDataSourceRegistry() {}

    public enum Trust { OFFICIAL, STRUCTURED_COMMUNITY, COMMUNITY_REFERENCE }

    public static final class Dataset {
        public final String id;
        public final String purpose;
        public final Trust trust;
        public final boolean svsRelevant;
        public final String source;

        Dataset(String id, String purpose, Trust trust, boolean svsRelevant, String source) {
            this.id=id; this.purpose=purpose; this.trust=trust; this.svsRelevant=svsRelevant; this.source=source;
        }
    }

    public static final List<Dataset> DATASETS = Collections.unmodifiableList(Arrays.asList(
        new Dataset("buildings", "Building and Fire Crystal progression", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured buildings dataset"),
        new Dataset("chief_charms", "Chief Charm levels, materials and power", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured chief-charms dataset"),
        new Dataset("chief_gear", "Chief Gear tiers and materials", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured chief-gear dataset"),
        new Dataset("furnace", "Furnace and Fire Crystal progression", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured furnace dataset"),
        new Dataset("hero_gear", "Hero Gear progression", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured gear-data dataset"),
        new Dataset("pets", "Pet progression and materials", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured pets dataset"),
        new Dataset("research", "Growth, Economy and Battle research", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured research_upgrades dataset"),
        new Dataset("state_age", "State age and feature timeline", Trust.STRUCTURED_COMMUNITY, false,
            "WSCO structured state-age/timeline datasets"),
        new Dataset("vip", "VIP progression", Trust.STRUCTURED_COMMUNITY, false,
            "WSCO structured VIP dataset"),
        new Dataset("war_academy", "War Academy / Helios T11 progression", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured war_academy dataset"),
        new Dataset("war_academy_t12", "Exalted T12 / Flame Tech progression", Trust.STRUCTURED_COMMUNITY, true,
            "WSCO structured war_academy_t12 dataset")
    ));

    /** Every SvS optimizer candidate must originate from a known SvS-relevant dataset. */
    public static boolean isSvsDataset(String id) {
        for (Dataset d : DATASETS) if (d.id.equals(id)) return d.svsRelevant;
        return false;
    }

    /** Unknown/unverified numeric values must never be auto-spent by an optimizer. */
    public static boolean canAutoScore(Trust trust, boolean valueVerified) {
        return valueVerified && (trust == Trust.OFFICIAL || trust == Trust.STRUCTURED_COMMUNITY);
    }
}
