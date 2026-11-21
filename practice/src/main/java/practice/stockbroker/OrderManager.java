package practice.stockbroker;

import java.util.Map;

class OrderManager {
    private final Map<String, User> users;
    private final OrderValidator validator;
    private final OrderExecutor executor;

    OrderManager(Map<String, User> users, OrderValidator validator, OrderExecutor executor) {
        this.users = Map.copyOf(users);
        this.validator = validator;
        this.executor = executor;
    }

    boolean placeOrder(String userId, Order order) {
        User user = users.get(userId);
        if (user == null || !validator.canPlace(user, order)) {
            return false;
        }
        executor.execute(order);
        return true;
    }
}
