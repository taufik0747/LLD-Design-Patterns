package practice.stockbroker;

import java.math.BigDecimal;
import java.util.Map;

public class StockBrokerDemo {
    public static void main(String[] args) {
        Stock sbi = new Stock("SBI", Exchange.NSE, new BigDecimal("530.00"));
        User keerti = new User("keerti", new BigDecimal("1000.00"), Map.of("SBI", 10));
        OrderManager broker = new OrderManager(Map.of(keerti.id(), keerti),
                new OrderValidator(), new OrderExecutor(new DemoExchangeConnector()));

        Order sellTen = new Order(sbi, Side.SELL, OrderType.MARKET, 10, null);
        System.out.println("Sell 10 accepted? " + broker.placeOrder("keerti", sellTen));

        Order sellEleven = new Order(sbi, Side.SELL, OrderType.MARKET, 11, null);
        System.out.println("Sell 11 accepted? " + broker.placeOrder("keerti", sellEleven));
    }
}
