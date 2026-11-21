# Stock broker in Java · one-hour LLD exercise

Port of the order-placement example in [Keerti Purswani's C++ stock broker](https://github.com/keertipurswani/Zerodha-Low-Level-Design/tree/41760d3e9a3ba92126659d40468be74e7b331901/stock%20broker). The interview goal is to take an order, decide whether the user may place it, and forward accepted orders to a simulated exchange.

## Start with a call, then write Order

Write this client call on the board first. It fixes the input and the observable outcome before you choose the supporting classes.

```java
Order sellTen = new Order(sbi, Side.SELL, OrderType.MARKET, 10, null);
boolean accepted = broker.placeOrder("keerti", sellTen);
```

| Minutes | What to write | Question it answers |
| --- | --- | --- |
| 0–5 | Agree on the example and sketch the call above. | Does success mean submitted or filled? Here it means submitted. |
| 5–15 | Write `Order`, `Stock`, and the enums together. | What do we need to express “sell 10 SBI shares on NSE at market”? The stock carries the exchange. A limit order also carries a limit price. |
| 15–25 | Write `User` and `OrderValidator.canPlace`. | What can reject this order? Check holdings for a sell and estimated cost against cash for a buy. |
| 25–35 | Write `ExchangeConnector.send` and a console implementation. | Where does the broker hand the order to another system? |
| 35–45 | Wire `OrderManager.placeOrder` and the demo. | Can one valid order reach the exchange? The small `OrderExecutor` mirrors the reference. In a timed exercise, calling the connector directly is sufficient until execution has more work. |
| 45–60 | Demonstrate the boundary cases and discuss one follow-up. | Selling 10 with 10 held passes. Selling 11 fails. An unknown user fails. For a follow-up, discuss two orders trying to spend the same balance. |

## Follow the request

1. `StockBrokerDemo` gives Keerti 10 SBI shares and constructs a market sell for 10.
2. `OrderManager` looks up the user and calls `OrderValidator`.
3. The validator accepts because 10 held is enough for 10 requested.
4. `OrderExecutor` passes the order to `ExchangeConnector`. The demo connector prints the submission.
5. A sell for 11 returns false at validation. Only the first order produces an exchange message.

## How this maps to the C++ code

| C++ | Java | Design choice |
| --- | --- | --- |
| `common.hpp`, `Stock`, `Order`, `User` | Enums and Java records | Use `BigDecimal` for money. A stock identifies its listing, so the order does not repeat its exchange. |
| `System::getUser`, `OrderMgr` | Injected user map and `OrderManager` | A local map makes the demo self-contained and avoids shared global state. |
| `OrderValidator` | `OrderValidator` | The C++ stub assumes success. Java checks an account snapshot. Market buys use the last price as an estimate, and limit buys use the limit price. |
| `OrderExecutioner`, singleton `EXCHConnector` | `OrderExecutor`, injected `ExchangeConnector` | The demo shares one connector instance. A test can provide another implementation. |
| Inventory, portfolio, watchlist, transaction, order status | Outside the order-submission path | The source demo does not exercise these. Holdings and a stock quote supply just the data this example needs. |

**Scope.** A successful submission does not change cash or holdings. The demo has no matching engine or exchange fills. To accept multiple live orders against one account, reserve cash or shares atomically at acceptance and release or settle the reservation on cancellation or fill.

## Run

In IntelliJ, open `StockBrokerDemo.java` in the existing Java practice module and run its `main` method. From the repository root in PowerShell with JDK 17 or later:

```powershell
$sources = (Get-ChildItem 'practice/src/main/java/practice/stockbroker/*.java').FullName
javac --release 17 -d out/stockbroker $sources
java -cp out/stockbroker practice.stockbroker.StockBrokerDemo
```

Expected output:

```text
Sent to NSE: SELL 10 SBI (MARKET)
Sell 10 accepted? true
Sell 11 accepted? false
```
