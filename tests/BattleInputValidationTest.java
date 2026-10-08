import com.hand33h.tulostaulu.BattleInputValidation;
public class BattleInputValidationTest {
    private static int checks;
    private static void close(double actual,double expected) {
        checks++; if(Math.abs(actual-expected)>1e-12) throw new AssertionError(actual+" != "+expected);
    }
    private static void rejects(Runnable action) {
        checks++; try {action.run();} catch(IllegalArgumentException expected) {return;} throw new AssertionError("Invalid input accepted");
    }
    public static void main(String[] args) {
        close(BattleInputValidation.parse(" 118,05 ","HP"),118.05);
        for(String bad:new String[]{"", "NaN", "Infinity", "1e309", "not a number"}) rejects(()->BattleInputValidation.parse(bad,"HP"));
        rejects(()->BattleInputValidation.troops(0,"troops"));
        rejects(()->BattleInputValidation.troops(-1,"troops"));
        rejects(()->BattleInputValidation.troops(1.5,"troops"));
        BattleInputValidation.troops(100000,"troops");
        BattleInputValidation.split(40,30,30,"A");
        rejects(()->BattleInputValidation.split(-10,50,60,"A"));
        rejects(()->BattleInputValidation.split(40,30,20,"A"));
        rejects(()->BattleInputValidation.nonNegative(-1,"bonus"));
        close(BattleInputValidation.scoreShare(100,100),0.5);
        close(BattleInputValidation.scoreShare(3,1),0.75);
        close(BattleInputValidation.scoreShare(Double.MAX_VALUE,Double.MAX_VALUE),0.5);
        close(BattleInputValidation.scoreShare(0,1),0);
        rejects(()->BattleInputValidation.scoreShare(0,0));
        rejects(()->BattleInputValidation.scoreShare(Double.NaN,1));
        System.out.println(checks+" battle input checks passed");
    }
}
