package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Human-readable diagnostics and pre-flight validation for Battle Simulator hero data. */
public final class HeroBattleDiagnostics {
    private HeroBattleDiagnostics() {}

    public static final class SelectionResult {
        public final boolean safe;
        public final String blockingWarning;
        public final String accuracyNotice;
        SelectionResult(boolean safe,String blockingWarning,String accuracyNotice){this.safe=safe;this.blockingWarning=blockingWarning;this.accuracyNotice=accuracyNotice;}
    }

    public static boolean isHealthy(){return HeroBattleCoverageValidation.missingSkillCoverage().isEmpty()&&HeroBattleCoverageValidation.nonMonotonicSkillScaling().isEmpty();}
    public static String statusLine(){return(isHealthy()?"Hero model OK — ":"Hero model warning — ")+HeroBattleCoverageValidation.coverageSummary();}

    public static String selectionWarning(String... selectedHeroes){if(selectedHeroes==null||selectedHeroes.length==0)return null;Set<String>seen=new HashSet<>();List<String>missing=HeroBattleCoverageValidation.missingSkillCoverage();for(String selected:selectedHeroes){String name=HeroBattleData.nameOf(selected);if(name==null||"None".equals(name))continue;if(!seen.add(name))return"Duplicate hero selected: "+name;if(missing.contains(name))return"Hero skill handling is not yet verified: "+name;}return null;}
    public static boolean selectionIsSafe(String... selectedHeroes){return selectionWarning(selectedHeroes)==null;}

    public static List<String> selectedConditionalOnlyHeroes(String... selectedHeroes){List<String>out=new ArrayList<>();if(selectedHeroes==null)return out;List<String>conditional=HeroBattleCoverageValidation.conditionalOnlySkillCoverage();Set<String>seen=new HashSet<>();for(String selected:selectedHeroes){String name=HeroBattleData.nameOf(selected);if(name==null||"None".equals(name)||!seen.add(name))continue;if(conditional.contains(name))out.add(name);}return out;}
    public static String conditionalOnlyNotice(String... selectedHeroes){List<String>heroes=selectedConditionalOnlyHeroes(selectedHeroes);if(heroes.isEmpty())return null;return"Conditional hero skills not flattened into permanent score: "+join(heroes);}

    /** One pre-flight result for both armies: blockers stop simulation, notices only explain accuracy limits. */
    public static SelectionResult validateBattleSelections(String[] attacker,String[] defender){
        String a=selectionWarning(attacker);if(a!=null)return new SelectionResult(false,"Attacker: "+a,null);
        String d=selectionWarning(defender);if(d!=null)return new SelectionResult(false,"Defender: "+d,null);
        List<String>notices=new ArrayList<>();String ac=conditionalOnlyNotice(attacker),dc=conditionalOnlyNotice(defender);if(ac!=null)notices.add("Attacker — "+ac);if(dc!=null)notices.add("Defender — "+dc);
        return new SelectionResult(true,null,notices.isEmpty()?null:join(notices));
    }

    public static String report(){StringBuilder out=new StringBuilder(statusLine());append(out,"Missing",HeroBattleCoverageValidation.missingSkillCoverage());append(out,"Scaling warnings",HeroBattleCoverageValidation.nonMonotonicSkillScaling());append(out,"Conditional-only",HeroBattleCoverageValidation.conditionalOnlySkillCoverage());append(out,"Side-sensitive",HeroBattleCoverageValidation.sideSensitiveSkillCoverage());return out.toString();}
    private static void append(StringBuilder out,String label,List<String>heroes){out.append("\n").append(label).append(": ").append(heroes==null||heroes.isEmpty()?"none":join(heroes));}
    private static String join(List<String>values){StringBuilder out=new StringBuilder();for(int i=0;i<values.size();i++){if(i>0)out.append(", ");out.append(values.get(i));}return out.toString();}
}
