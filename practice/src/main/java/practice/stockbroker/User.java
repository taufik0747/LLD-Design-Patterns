package practice.stockbroker;

import java.math.BigDecimal;
import java.util.Map;

// A single account snapshot for the pre-trade check.
record User(String id, BigDecimal cash, Map<String, Integer> holdings) {
    User {
        holdings = Map.copyOf(holdings);
    }

    int sharesOf(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }
}
