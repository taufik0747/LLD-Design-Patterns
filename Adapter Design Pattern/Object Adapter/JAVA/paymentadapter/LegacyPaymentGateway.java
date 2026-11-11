package paymentadapter;

public class LegacyPaymentGateway {

    public void charge(long paise){
        System.out.println("Charged "+ paise+" in paise");
    }
}
