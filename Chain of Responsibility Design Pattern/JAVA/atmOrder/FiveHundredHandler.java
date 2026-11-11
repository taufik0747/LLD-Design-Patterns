package atmOrder;

public class FiveHundredHandler extends CashHandler{
    @Override
    public void dispense(int amount) {
        int notes500=amount/500;
        amount%=500;
        System.out.println("500 notes: "+notes500);

        if (next != null && amount > 0) {
            next.dispense(amount);
        }
    }
}
