package atmOrder;

public class Main {
    public static void main(String[] args) {
        CashHandler h2000 = new TwoThousandHandler();
        CashHandler h500 = new FiveHundredHandler();
        CashHandler h100 = new HundredHandler();

        h2000.setNext(h500);
        h500.setNext(h100);
        h2000.dispense(2600);
    }
}
