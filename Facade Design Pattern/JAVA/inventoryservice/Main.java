package inventoryservice;

public class Main {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        PaymentService payment = new PaymentService();
        ShippingService shipping = new ShippingService();

        // TODO 6: Create OrderFacade
        OrderFacade orderFacade = new OrderFacade(inventory, payment, shipping);

        // TODO 7: Place an order
        orderFacade.placeOrder();
    }
}
