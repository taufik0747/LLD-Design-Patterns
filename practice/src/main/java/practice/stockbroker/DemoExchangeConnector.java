package practice.stockbroker;

class DemoExchangeConnector implements ExchangeConnector {
    @Override
    public void send(Order order) {
        System.out.printf("Sent to %s: %s %d %s (%s)%n",
                order.stock().exchange(), order.side(), order.quantity(),
                order.stock().symbol(), order.type());
    }
}
