import java.math.BigDecimal;
import java.util.*;

enum Market { NSE, BSE }
enum Side { BUY, SELL }
record Stock(Market market, String symbol) {}

class User {
    final String name;
    BigDecimal cash;
    BigDecimal reservedCash = BigDecimal.ZERO;
    final Map<Stock, Integer> shares = new HashMap<>();
    final Map<Stock, Integer> reservedShares = new HashMap<>();

    User(String name, String initialCash) {
        this.name = name;
        this.cash = new BigDecimal(initialCash);
    }

    void addShares(Stock stock, int quantity) { shares.merge(stock, quantity, Integer::sum); }

    void reserveBuy(BigDecimal price, int quantity) {
        BigDecimal required = price.multiply(BigDecimal.valueOf(quantity));
        if (cash.compareTo(required) < 0) throw new IllegalArgumentException("Insufficient cash");
        cash = cash.subtract(required);
        reservedCash = reservedCash.add(required);
    }

    void reserveSell(Stock stock, int quantity) {
        if (shares.getOrDefault(stock, 0) < quantity)
            throw new IllegalArgumentException("Insufficient shares");
        shares.merge(stock, -quantity, Integer::sum);
        reservedShares.merge(stock, quantity, Integer::sum);
    }

    void receiveBuy(Stock stock, BigDecimal limit, BigDecimal executed, int qty) {
        BigDecimal held = limit.multiply(BigDecimal.valueOf(qty));
        reservedCash = reservedCash.subtract(held);
        cash = cash.add(held.subtract(executed.multiply(BigDecimal.valueOf(qty))));
        shares.merge(stock, qty, Integer::sum);
    }

    void receiveSell(Stock stock, BigDecimal executed, int qty) {
        reservedShares.merge(stock, -qty, Integer::sum);
        cash = cash.add(executed.multiply(BigDecimal.valueOf(qty)));
    }

    void show() {
        System.out.println(name + ": cash=" + cash + ", reserved=" + reservedCash
                + ", shares=" + shares + ", reserved shares=" + reservedShares);
    }
}

class Order {
    final long id;
    final User user;
    final Stock stock;
    final Side side;
    final BigDecimal price;
    int remaining;

    Order(long id, User user, Stock stock, Side side, int qty, BigDecimal price) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.side = side;
        this.remaining = qty;
        this.price = price;
    }
}

record Trade(Stock stock, int quantity, BigDecimal price) {}

class OrderBook {
    // Priority: best price first; for equal price, the oldest order first.
    final PriorityQueue<Order> buys = new PriorityQueue<>(
            Comparator.comparing((Order o) -> o.price, Comparator.reverseOrder())
                      .thenComparingLong(o -> o.id));
    final PriorityQueue<Order> sells = new PriorityQueue<>(
            Comparator.comparing((Order o) -> o.price)
                      .thenComparingLong(o -> o.id));
}

class StockExchange {
    private final Map<Stock, OrderBook> books = new HashMap<>();
    private final List<Trade> trades = new ArrayList<>();
    private long nextId = 1;

    void placeLimit(User user, Stock stock, Side side, int quantity, BigDecimal price) {
        if (user == null || stock == null || side == null || quantity <= 0 ||
                price == null || price.signum() <= 0)
            throw new IllegalArgumentException("Invalid order");

        if (side == Side.BUY) user.reserveBuy(price, quantity);
        else user.reserveSell(stock, quantity);

        Order order = new Order(nextId++, user, stock, side, quantity, price);
        OrderBook book = books.computeIfAbsent(stock, ignored -> new OrderBook());
        PriorityQueue<Order> opposite = side == Side.BUY ? book.sells : book.buys;

        while (order.remaining > 0 && !opposite.isEmpty()) {
            Order resting = opposite.peek();
            boolean crosses = side == Side.BUY
                    ? price.compareTo(resting.price) >= 0
                    : price.compareTo(resting.price) <= 0;
            if (!crosses) break;

            int filled = Math.min(order.remaining, resting.remaining);
            BigDecimal executedPrice = resting.price; // Resting order sets trade price.
            Order buyer = side == Side.BUY ? order : resting;
            Order seller = side == Side.SELL ? order : resting;

            buyer.user.receiveBuy(stock, buyer.price, executedPrice, filled);
            seller.user.receiveSell(stock, executedPrice, filled);
            buyer.remaining -= filled;
            seller.remaining -= filled;
            trades.add(new Trade(stock, filled, executedPrice));
            System.out.println("TRADE " + filled + " " + stock + " @ " + executedPrice);
            if (resting.remaining == 0) opposite.poll();
        }

        if (order.remaining > 0) {
            if (side == Side.BUY) book.buys.add(order);
            else book.sells.add(order);
        }
    }

    List<Trade> trades() { return List.copyOf(trades); }
}

public class Main {
    public static void main(String[] args) {
        Stock tcs = new Stock(Market.NSE, "TCS");
        User alice = new User("Alice", "10000");
        User bob = new User("Bob", "1000");
        bob.addShares(tcs, 5);
        StockExchange exchange = new StockExchange();

        exchange.placeLimit(bob, tcs, Side.SELL, 5, new BigDecimal("100"));
        exchange.placeLimit(alice, tcs, Side.BUY, 3, new BigDecimal("110"));
        exchange.placeLimit(alice, tcs, Side.BUY, 2, new BigDecimal("100"));

        alice.show(); // cash=9500, reserved=0, shares=5
        bob.show();   // cash=1500, shares=0
        System.out.println("Trades: " + exchange.trades());
    }
}
