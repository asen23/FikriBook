package Menu.Transaction;

import Menu.Transaction.Cart.CartItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Transaction {
    private final String id;
    private final LocalDateTime dateTime;
    private final List<Detail> details;
    private final String buyerId;

    public Transaction(String id, LocalDateTime dateTime, List<CartItem> details, String buyerId) {
        this.id = id;
        this.dateTime = dateTime;
        this.details = details
              .stream()
              .map(cartItem -> new Detail(cartItem.bookId, cartItem.quantity))
              .collect(Collectors.toList());
        this.buyerId = buyerId;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getBuyerId() {
        return buyerId;
    }
}
