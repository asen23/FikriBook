package Menu.Transaction.Cart;

public class CartItem {
    public String bookId;
    public int quantity;

    public CartItem(String bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
