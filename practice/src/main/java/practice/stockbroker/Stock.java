package practice.stockbroker;

import java.math.BigDecimal;

record Stock(String symbol, Exchange exchange, BigDecimal lastPrice) {}
