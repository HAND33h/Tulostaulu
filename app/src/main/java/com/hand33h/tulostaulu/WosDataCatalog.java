package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Central catalog for externally maintained WOS datasets used by calculators.
 * Data is not silently trusted: every dataset has a source, verification date
 * and policy. Download/validation can be added without changing calculator UI.
 */
public final class WosDataCatalog {
    public static final String CATALOG_VERSION = "2026.09.08";
    public static final String VERIFIED_AT = "2026-09-08";

    public static final class Dataset {
        public final String id;
        public final String label;
        public final String source;
        public final String policy;
        Dataset(String id,String label,String source,String policy){this.id=id;this.label=label;this.source=source;this.policy=policy;}
    }

    public static final List<Dataset> DATASETS = Collections.unmodifiableList(Arrays.asList(
        new Dataset("buildings","Buildings","WSCO","verified-structured"),
        new Dataset("furnace","Furnace / Fire Crystal","WSCO","verified-structured"),
        new Dataset("chief-gear","Chief Gear","WSCO","verified-structured"),
        new Dataset("chief-charms","Chief Charms","WSCO","verified-structured"),
        new Dataset("gear-data","Hero Gear","WSCO","verified-structured"),
        new Dataset("koi","King of Icefield","WSCO","season-aware"),
        new Dataset("pets","Pets","WSCO","verified-structured"),
        new Dataset("research","Research","WSCO","verified-structured"),
        new Dataset("state-age","State Age / Timeline","WSCO","verified-structured"),
        new Dataset("vip","VIP","WSCO","verified-structured"),
        new Dataset("war-academy","War Academy","WSCO","verified-structured"),
        new Dataset("war-academy-t12","T12 / Exalted Troops","WSCO","verified-structured")
    ));

    public static String statusText(){
        StringBuilder b=new StringBuilder();
        b.append("WOS DATA CATALOG ").append(CATALOG_VERSION).append("\nVerified: ").append(VERIFIED_AT).append("\n\n");
        for(Dataset d:DATASETS)b.append("✓ ").append(d.label).append("  • ").append(d.source).append("  • ").append(d.policy).append("\n");
        b.append("\nPolicy: verified datasets, OCR observations and community advice remain separate. Season-dependent event values are never frozen as universal constants.");
        return b.toString();
    }

    private WosDataCatalog(){}
}
