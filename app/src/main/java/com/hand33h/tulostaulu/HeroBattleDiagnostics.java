package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Human-readable diagnostics and pre-flight validation for Battle Simulator hero data. */
public final class HeroBattleDiagnostics {
    private HeroBattleDiagnostics() {}
    public static final class SelectionResult {public final boolean safe;public final String blockingWarning;public final String accuracyNotice;SelectionResult(boolean safe,String blockingWarning,String accuracyNotice){this.safe=safe;this.blockingWarning=blockingWarning;this.accuracyNotice=accuracyNotice;}}
    public static boolean isHealthy(){return HeroBattleCoverageValidation.missingSkillCoverage().isEmpty()&&HeroBattleCoverageValidation.nonMonotonicSkillScaling().isEmpty();}
    public static String statusLine(){return(isHealthy()?"Hero model OK — ":"Hero model warning — ")+HeroBattleCoverageValidation.coverageSummary();}
    public static String selectionWarning(String... selectedHeroes){if(selectedHeroes==null||selectedHeroes.length==0)return null;Set<String>seen=new HashSet<>();List<String>missing=HeroBattleCoverageValidation.missingSkillCoverage();for(String selected:selectedHeroes){String name=HeroBattleData.nameOf(selected);if(name==null||"None".equals(name))continue;if(!seen.add(name))return"Duplicate hero selected: "+name;if(missing.contains(name))return"Hero skill handling is not yet verified: "+name;}return null;}
    public static boolean selectionIsSafe(String... selectedHeroes){return selectionWarning(selectedHeroes)==null;}
    public static List<String> selectedConditionalOnlyHeroes(String... selectedHeroes){List<String>out=new ArrayList<>();if(selectedHeroes==null)return out;List<String>conditional=HeroBattleCoverageValidation.conditionalOnlySkillCoverage();Set<String>seen=new HashSet<>();for(String selected:selectedHeroes){String name=HeroBattleData.nameOf(selected);if(name==null||"None".equals(name)||!seen.add(name))continue;if(conditional.contains(name))out.add(name);}return out;}
    public static String conditionalOnlyNotice(String... selectedHeroes){List<String>heroes=selectedConditionalOnlyHeroes(selectedHeroes);if(heroes.isEmpty())return null;return"Conditional hero skills not flattened into permanent score: "+join(heroes);}

    /** Detailed accuracy notice using exact structured mechanics where available. */
    public static String conditionalMechanicsNotice(String[] heroes,int[] skillLevels){if(heroes==null||skillLevels==null)return null;StringBuilder out=new StringBuilder();int n=Math.min(heroes.length,skillLevels.length);for(int i=0;i<n;i++){String detail=HeroConditionalEffects.summary(heroes[i],skillLevels[i]);if(detail==null)continue;if(out.length()>0)out.append("\n");out.append(detail);}return out.length()==0?null:out.toString();}

    public static SelectionResult validateBattleSelections(String[] attacker,int[] attackerLevels,String[] defender,int[] defenderLevels){String a=selectionWarning(attacker);if(a!=null)return new SelectionResult(false,"Attacker: "+a,null);String d=selectionWarning(defender);if(d!=null)return new SelectionResult(false,"Defender: "+d,null);List<String>notices=new ArrayList<>();addSideNotice(notices,"Attacker",attacker,attackerLevels);addSideNotice(notices,"Defender",defender,defenderLevels);return new SelectionResult(true,null,notices.isEmpty()?null:joinLines(notices));}
    /** Compatibility overload for callers that do not yet pass selected skill levels. */
    public static SelectionResult validateBattleSelections(String[] attacker,String[] defender){return validateBattleSelections(attacker,null,defender,null);}
    private static void addSideNotice(List<String>notices,String side,String[]heroes,int[]levels){String conditional=conditionalOnlyNotice(heroes);if(conditional!=null)notices.add(side+" — "+conditional);String mechanics=conditionalMechanicsNotice(heroes,levels);if(mechanics!=null)notices.add(side+" exact conditional mechanics:\n"+mechanics);}

    public static String report(){StringBuilder out=new StringBuilder(statusLine());append(out,"Missing",HeroBattleCoverageValidation.missingSkillCoverage());append(out,"Scaling warnings",HeroBattleCoverageValidation.nonMonotonicSkillScaling());append(out,"Conditional-only",HeroBattleCoverageValidation.conditionalOnlySkillCoverage());append(out,"Side-sensitive",HeroBattleCoverageValidation.sideSensitiveSkillCoverage());return out.toString();}
    private static void append(StringBuilder out,String label,List<String>heroes){out.append("\n").append(label).append(": ").append(heroes==null||heroes.isEmpty()?"none":join(heroes));}
    private static String join(List<String>values){StringBuilder out=new StringBuilder();for(int i=0;i<values.size();i++){if(i>0)out.append(", ");out.append(values.get(i));}return out.toString();}
    private static String joinLines(List<String>values){StringBuilder out=new StringBuilder();for(int i=0;i<values.size();i++){if(i>0)out.append("\n");out.append(values.get(i));}return out.toString();}
}
