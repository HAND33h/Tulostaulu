package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Chief Gear progression used by Battle Simulator.
 * Exact stat/deployment micro-stages are based on the published WoSTools table checked 2026-09-08.
 * Set-bonus existence is known, but exact per-quality percentages are not publicly tabulated reliably,
 * so this class detects eligibility without inventing a numeric set bonus.
 */
public final class ChiefGearData {
    private ChiefGearData() {}

    public static final class Stage {
        public final String name;
        public final double stat;
        public final int deployment;
        public final long power;
        public final String quality;
        Stage(String n,double s,int d,long p,String q){name=n;stat=s;deployment=d;power=p;quality=q;}
    }

    private static final List<Stage> STAGES=new ArrayList<>();
    public static final String[] NAMES;

    static {
        add("None",0,0,0,"None");
        add("Green",9.35,0,224400,"Green");
        add("Green ★",12.75,0,306000,"Green");
        add("Blue",17.00,0,408000,"Blue");
        add("Blue ★",21.25,0,510000,"Blue");
        add("Blue ★★",25.50,0,612000,"Blue");
        add("Blue ★★★",29.75,0,714000,"Blue");
        add("Purple",34.00,0,816000,"Purple");
        add("Purple ★",36.89,0,885360,"Purple");
        add("Purple ★★",39.78,0,954720,"Purple");
        add("Purple ★★★",42.67,0,1024080,"Purple");
        add("Purple T1",45.56,0,1093440,"Purple");
        add("Purple T1 ★",48.45,0,1162800,"Purple");
        add("Purple T1 ★★",51.34,0,1232160,"Purple");
        add("Purple T1 ★★★",54.23,0,1301520,"Purple");
        add("Gold",56.78,0,1362720,"Gold");
        add("Gold ★",59.33,0,1423920,"Gold");
        add("Gold ★★",61.88,0,1485120,"Gold");
        add("Gold ★★★",64.43,0,1546320,"Gold");
        add("Gold T1",66.98,0,1607520,"Gold");
        add("Gold T1 ★",69.53,0,1668720,"Gold");
        add("Gold T1 ★★",72.08,0,1729920,"Gold");
        add("Gold T1 ★★★",74.63,0,1791120,"Gold");
        add("Gold T2",77.18,0,1852320,"Gold");
        add("Gold T2 ★",79.73,0,1913520,"Gold");
        add("Gold T2 ★★",82.28,0,1974720,"Gold");
        add("Gold T2 ★★★",85.00,0,2040000,"Gold");
        add("Gold T2 ★★★ +1",86.06,10,2065500,"Gold");
        add("Gold T2 ★★★ +2",87.13,20,2091000,"Gold");
        add("Gold T2 ★★★ +3",88.19,30,2116500,"Gold");

        addSeries("Red",new String[]{"-","-","-","-","★","★","★","★","★★","★★","★★","★★","★★★","★★★ +1","★★★ +2","★★★ +3"},
                new double[]{89.25,90.31,91.38,92.44,93.50,94.56,95.63,96.69,97.75,98.81,99.88,100.94,102.00,103.06,104.13,105.19},
                new int[]{40,50,60,70,80,90,100,110,120,130,140,150,160,260,270,280},2142000,25500,"Red");
        addSeries("Red T1",new String[]{"-","-","-","-","★","★","★","★","★★","★★","★★","★★","★★★","★★★ +1","★★★ +2","★★★ +3"},
                new double[]{106.25,107.31,108.38,109.44,110.50,111.56,112.63,113.69,114.75,115.81,116.88,117.94,119.00,120.06,121.13,122.19},
                new int[]{290,300,310,320,330,340,350,360,370,380,390,400,410,510,520,530},2550000,25500,"Red");
        addSeries("Red T2",new String[]{"-","-","-","-","★","★","★","★","★★","★★","★★","★★","★★★","★★★ +1","★★★ +2","★★★ +3"},
                new double[]{123.25,124.31,125.38,126.44,127.50,128.56,129.63,130.69,131.75,132.81,133.88,134.94,136.00,137.06,138.13,139.19},
                new int[]{540,550,560,570,580,590,600,610,620,630,640,650,660,760,770,780},2958000,25500,"Red");
        addSeries("Red T3",new String[]{"-","-","-","-","★","★","★","★","★★","★★","★★","★★","★★★","★★★ +1","★★★ +2","★★★ +3","★★★ +4"},
                new double[]{140.25,141.31,142.38,143.44,144.50,145.56,146.63,147.69,148.75,149.81,150.88,151.94,153.00,154.70,156.40,158.10,159.80},
                new int[]{790,800,810,820,830,840,850,860,870,880,890,900,910,1010,1020,1030,1040},3366000,40800,"Red");
        addSeries("Red T4",new String[]{"-","-","-","-","-","★","★","★","★","★","★★","★★","★★","★★","★★","★★★","★★★ +1","★★★ +2","★★★ +3","★★★ +4"},
                new double[]{161.50,163.20,164.90,166.60,168.30,170.00,171.70,173.40,175.10,176.80,178.50,180.20,181.90,183.60,185.30,187.00,188.70,190.40,192.10,193.80},
                new int[]{1050,1060,1070,1080,1090,1100,1110,1120,1130,1140,1150,1160,1170,1180,1190,1200,1300,1310,1320,1330},3876000,40800,"Red");
        addSeries("Red T5",new String[]{"-","-","-","-","-","★","★","★","★","★","★★","★★","★★","★★","★★","★★★","★★★ +1","★★★ +2","★★★ +3","★★★ +4"},
                new double[]{195.50,197.20,198.90,200.60,202.30,204.00,205.70,207.40,209.10,210.80,212.50,214.20,215.90,217.60,219.30,221.00,222.70,224.40,226.10,227.80},
                new int[]{1340,1350,1360,1370,1380,1390,1400,1410,1420,1430,1440,1450,1460,1470,1480,1490,1590,1600,1610,1620},4692000,40800,"Red");
        addSeries("Red T6",new String[]{"-","-","-","-","-","★","★","★","★","★","★★","★★","★★","★★","★★","★★★"},
                new double[]{229.50,231.20,232.90,234.60,236.30,238.00,239.70,241.40,243.10,244.80,246.50,248.20,249.90,251.60,253.30,255.00},
                new int[]{1630,1640,1650,1660,1670,1680,1690,1700,1710,1720,1730,1740,1750,1760,1770,1780},5508000,40800,"Red");

        NAMES=new String[STAGES.size()];for(int i=0;i<STAGES.size();i++)NAMES[i]=STAGES.get(i).name;
    }

    private static void add(String n,double s,int d,long p,String q){STAGES.add(new Stage(n,s,d,p,q));}
    private static void addSeries(String tier,String[] labels,double[] stats,int[] caps,long powerStart,long powerStep,String quality){
        for(int i=0;i<stats.length;i++)add(tier+" "+labels[i]+"  ["+String.format(Locale.US,"%.2f%%",stats[i])+"]",stats[i],caps[i],powerStart+powerStep*i,quality);
    }
    public static Stage stage(int index){return STAGES.get(Math.max(0,Math.min(STAGES.size()-1,index)));}
    public static double stat(int index){return stage(index).stat;}
    public static int deployment(int index){return stage(index).deployment;}
    public static long power(int index){return stage(index).power;}
    public static String quality(int index){return stage(index).quality;}
    public static int size(){return STAGES.size();}

    public static String setStatus(int[] positions){
        int green=0,blue=0,purple=0,gold=0,red=0;
        for(int p:positions){String q=quality(p);if("Green".equals(q))green++;else if("Blue".equals(q))blue++;else if("Purple".equals(q))purple++;else if("Gold".equals(q))gold++;else if("Red".equals(q))red++;}
        int max=Math.max(red,Math.max(gold,Math.max(purple,Math.max(blue,green))));
        String q=max==red?"Red":max==gold?"Gold":max==purple?"Purple":max==blue?"Blue":max==green?"Green":"None";
        if(max>=6)return "6-piece "+q+" set eligible: all-troop Defense + Attack set bonuses exist; exact set % not auto-applied because no reliable exact public table was found.";
        if(max>=3)return "3-piece "+q+" set eligible: all-troop Defense set bonus exists; exact set % not auto-applied because no reliable exact public table was found.";
        return "No 3/6-piece same-quality set bonus active.";
    }
}
