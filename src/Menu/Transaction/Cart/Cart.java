package Menu.Transaction.Cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addBook(String bookId, int quantity) {
        items.add(new CartItem(bookId, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }
}
