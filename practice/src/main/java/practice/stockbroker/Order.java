package practice.stockbroker;

import java.math.BigDecimal;
import java.util.Objects;

record Order(Stock stock, Side side, OrderType type, int quantity, BigDecimal limitPrice) {
    Order {
        Objects.requireNonNull(stock);
        Objects.requireNonNull(side);
        Objects.requireNonNull(type);
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (type == OrderType.LIMIT && (limitPrice == null || limitPrice.signum() <= 0)) {
            throw new IllegalArgumentException("A limit order needs a positive limit price");
        }
        if (type == OrderType.MARKET && limitPrice != null) {
            throw new IllegalArgumentException("A market order has no limit price");
        }
    }

    BigDecimal estimatedCost() {
        BigDecimal unitPrice = type == OrderType.LIMIT ? limitPrice : stock.lastPrice();
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
