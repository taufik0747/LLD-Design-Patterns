package atmOrder;

public abstract class CashHandler {

    protected CashHandler next;

    public void setNext(CashHandler next){
        this.next=next;
    }

    public abstract void dispense(int amount);
}
