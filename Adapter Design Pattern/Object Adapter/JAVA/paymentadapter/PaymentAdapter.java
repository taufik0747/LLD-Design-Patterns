package paymentadapter;

public class PaymentAdapter implements PaymentProcessor{
  private final  LegacyPaymentGateway legacyPaymentGateway;

    public PaymentAdapter(LegacyPaymentGateway legacyPaymentGateway){
        this.legacyPaymentGateway=legacyPaymentGateway;
    }
    @Override
    public void pay(int rupees) {
        long conversion =  rupees*100L;
        legacyPaymentGateway.charge(conversion);
    }
}
