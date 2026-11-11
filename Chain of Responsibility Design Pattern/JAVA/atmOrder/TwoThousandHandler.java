package atmOrder;

public class TwoThousandHandler extends CashHandler{
    @Override
    public void dispense(int amount) {
        int notes2000=amount/2000;
        amount%=2000;
        System.out.println("2000 notes: "+notes2000);

        if (next != null && amount > 0) {
            next.dispense(amount);
        }
    }
}
