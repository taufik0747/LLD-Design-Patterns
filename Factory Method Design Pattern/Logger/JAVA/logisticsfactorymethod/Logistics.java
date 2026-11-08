package logisticsfactorymethod;

public abstract class Logistics {

    public abstract Transport createTransport();

    public String planDelivery(){
        Transport instance = createTransport();
       return instance.deliver();
    }
}
