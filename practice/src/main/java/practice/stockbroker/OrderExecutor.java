package practice.stockbroker;

record OrderExecutor(ExchangeConnector exchange) {
    void execute(Order order) {
        exchange.send(order);
    }
}
