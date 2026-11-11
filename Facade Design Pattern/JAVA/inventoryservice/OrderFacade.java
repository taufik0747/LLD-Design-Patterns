package inventoryservice;

public class OrderFacade {
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final ShippingService shippingService;

    // TODO 2: Constructor injection
    public OrderFacade(InventoryService inventoryService,
                       PaymentService paymentService,
                       ShippingService shippingService) {
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
        this.shippingService = shippingService;
    }

    public void placeOrder() {
        // TODO 3: Check stock
        if (inventoryService.checkStock()) {
            // TODO 4: Process payment only if stock exists
            paymentService.processPayment();

            // TODO 5: Create shipment after payment
            shippingService.createShipment();
        } else {
            System.out.println("Order failed: Out of stock");
        }
    }
}
