package Menu.User;

import Menu.Transaction.Cart.Cart;
import Menu.Transaction.Cart.CartItem;

import java.time.LocalDate;
import java.util.stream.Stream;

public class Buyer extends User{
    private final LocalDate dob;
    private String address;
    private String phoneNumber;
    private final Cart cart;

    public Buyer(
          String id,
          String name,
          String email,
          String password,
          LocalDate dob,
          String address,
          String phoneNumber
    ) {
        super(id, name, email, password);
        this.dob = dob;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart();
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Stream<CartItem> getCartItem() {
        return cart.getItems();
    }

    public void addCartItem(String bookId, int quantity) {
        cart.addBook(bookId, quantity);
    }

    public void editCartItem(String bookId, int quantity) {
        cart.editBookQuantity(bookId, quantity);
    }

    @Override
    public UserType getUserType() {
        return UserType.Buyer;
    }
}
