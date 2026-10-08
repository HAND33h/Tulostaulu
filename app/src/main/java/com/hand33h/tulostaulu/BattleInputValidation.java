package com.hand33h.tulostaulu;

/** Input checks only; does not alter verified hero values or combat mechanics. */
public final class BattleInputValidation {
    private BattleInputValidation() {}

    public static double parse(String raw, String label) {
        if (raw == null || raw.trim().isEmpty()) throw new IllegalArgumentException(label + ": value is required / arvo puuttuu");
        final double value;
        try { value = Double.parseDouble(raw.trim().replace(',', '.')); }
        catch (NumberFormatException e) { throw new IllegalArgumentException(label + ": enter a number / syötä numero"); }
        if (!Double.isFinite(value)) throw new IllegalArgumentException(label + ": enter a finite number / virheellinen numero");
        return value;
    }

    public static void nonNegative(double value, String label) {
        if (!Double.isFinite(value) || value < 0) throw new IllegalArgumentException(label + ": must be zero or positive / vähintään 0");
    }

    public static void troops(double count, String label) {
        if (!Double.isFinite(count) || count <= 0 || count != Math.floor(count))
            throw new IllegalArgumentException(label + ": enter a positive whole number / positiivinen kokonaisluku");
    }

    public static void split(double infantry, double lancer, double marksman, String label) {
        double[] parts={infantry,lancer,marksman};
        for(double part:parts) if(!Double.isFinite(part) || part < 0 || part > 100)
            throw new IllegalArgumentException(label + ": each troop share must be 0–100 %");
        if(Math.abs(infantry+lancer+marksman-100)>0.2)
            throw new IllegalArgumentException(label + ": Infantry + Lancer + Marksman must total 100 %");
    }

    public static double scoreShare(double attacker, double defender) {
        nonNegative(attacker,"Attacker score");
        nonNegative(defender,"Defender score");
        double scale=Math.max(attacker,defender);
        if(scale==0) throw new IllegalArgumentException("Both combat scores are zero");
        double a=attacker/scale,d=defender/scale;
        return a/(a+d);
    }
}
