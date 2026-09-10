package com.hand33h.tulostaulu;

/** Known real WOS report fixture used to regression-check screenshot/import math. */
public final class BattleReportCalibrationFixture {
    private BattleReportCalibrationFixture() {}

    public static BattleReportSnapshot bunnyKingReport() {
        BattleReportSnapshot r = new BattleReportSnapshot();
        r.sourceMailId = "1840789510431086";
        r.attacker.troops = 240360;
        r.attacker.losses = 0; r.attacker.injured = 108163; r.attacker.lightlyInjured = 132197; r.attacker.survivors = 0;
        r.defender.troops = 4741128;
        r.defender.losses = 0; r.defender.injured = 16516; r.defender.lightlyInjured = 30669; r.defender.survivors = 4693943;

        set(r.attacker.stats[0],2463.7,2295.5,1329.3,1335.4); set(r.defender.stats[0],1333.4,1219.9,840.9,826.5);
        set(r.attacker.stats[1],2221.0,2046.7,1234.6,1205.9); set(r.defender.stats[1],1538.7,1443.3,822.9,869.5);
        set(r.attacker.stats[2],2509.7,2314.3,1508.5,1277.7); set(r.defender.stats[2],1793.8,1652.6,822.9,840.0);
        return r;
    }

    public static boolean passesValidation() {
        return BattleReportImportValidator.validate(bunnyKingReport()).usable;
    }

    private static void set(BattleReportSnapshot.TroopStats s,double a,double d,double l,double h){
        s.attack=a; s.defense=d; s.lethality=l; s.health=h;
    }
}
