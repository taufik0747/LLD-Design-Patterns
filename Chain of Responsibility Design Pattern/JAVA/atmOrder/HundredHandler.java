package atmOrder;

public class HundredHandler extends CashHandler{
    @Override
    public void dispense(int amount) {
        int notes100=amount/100;
        amount%=100;
        System.out.println("100 notes: "+notes100);

        if (next != null && amount > 0) {
            System.out.println("Still left "+amount);
        }
    }
}
