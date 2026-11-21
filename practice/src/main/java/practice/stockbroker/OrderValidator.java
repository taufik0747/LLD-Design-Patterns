package practice.stockbroker;

class OrderValidator {
    boolean canPlace(User user, Order order) {
        if (order.side() == Side.SELL) {
            return user.sharesOf(order.stock().symbol()) >= order.quantity();
        }
        return user.cash().compareTo(order.estimatedCost()) >= 0;
    }
}
