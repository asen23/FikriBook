package Menu.Transaction.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addBook(String bookId, int quantity) {
        items.add(new CartItem(bookId, quantity));
    }

    public Stream<CartItem> getItems() {
        return items.stream();
    }

    public void editBookQuantity(String bookId, int quantity) {
        if(quantity == 0) {
            items.removeIf(cart -> cart.getBookId().equals(bookId));
        }
        items.forEach(cart -> {
            if(cart.getBookId().equals(bookId)){
                cart.setQuantity(quantity);
            }
        });
    }
}
