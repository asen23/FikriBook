package Menu.User;

import Helper.Helper;
import Menu.Transaction.Cart.CartItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class UserManager {
    private static final UserManager userManager = new UserManager();
    private final ArrayList<User> users = new ArrayList<>();
    private User currentUser = null;

    private UserManager() {
        users.add(new Owner(generateId(), "Owner", "owner@fikribook.com", "idk"));
        if (Helper.isDummyEnabled()) {
            users.add(new Admin(
                    generateId(),
                    "Admin1",
                    "admin1@fikribook.com",
                    "Admin1",
                    true
            ));
            users.add(new Admin(
                    generateId(),
                    "Admin2",
                    "admin2@fikribook.com",
                    "Admin2",
                    false
            ));
            users.add(new Admin(
                    generateId(),
                    "Admin3",
                    "admin3@fikribook.com",
                    "Admin3",
                    true
            ));
            users.add(new Buyer(
                    "user-fba9b13e-c438-48cc-ad65-670c3d356e40",
                    "buyer1",
                    "buyer1@buyer.com",
                    "buyer1",
                    LocalDate.of(2000, 1, 1),
                    "buyer1",
                    "buyer1"
            ));
            users.add(new Buyer(
                    "user-adb4ff58-8d7a-45e6-a733-540ea4aa2545",
                    "buyer2",
                    "buyer2@buyer.com",
                    "buyer2",
                    LocalDate.of(2000, 2, 2),
                    "buyer2",
                    "buyer2"
            ));
            users.add(new Buyer(
                    "user-9970db0b-048f-4321-9cd8-b7717818e456",
                    "buyer3",
                    "buyer3@buyer.com",
                    "buyer3",
                    LocalDate.of(2000, 3, 3),
                    "buyer3",
                    "buyer3"
            ));
            users.add(new Buyer(
                    "user-26963a6d-1c91-4f09-92dc-865249a80d0f",
                    "buyer4",
                    "buyer4@buyer.com",
                    "buyer4",
                    LocalDate.of(2000, 4, 4),
                    "buyer4",
                    "buyer4"
            ));
        }
    }

    public static UserManager getInstance() {
        return userManager;
    }

    private String generateId() {
        return Helper.generateId("user");
    }

    private Optional<User> getUserWithEmail(String email) {
        Optional<User> user = users
                .stream()
                .filter((u) -> u.getEmail().equals(email))
                .findFirst();
        return user;
    }

    public String getEmailWithId(String id) {
        Optional<User> user = users
                .stream()
                .filter((u) -> u.getId().equals(id))
                .findFirst();
        return user.map(User::getEmail).orElse("");
    }

    public User login(String email, String password) throws Exception {
        Optional<User> user = getUserWithEmail(email);
        if (!user.isPresent()) throw new Exception("Email not found!");
        User currentUser = user.orElse(null);
        if (!currentUser.getPassword().equals(password)) throw new Exception("Wrong password!");
        return currentUser;
    }

    public void register(
            String name,
            String email,
            String password,
            LocalDate dob,
            String address,
            String phoneNumber
    ) throws Exception {
        Optional<User> user = getUserWithEmail(email);
        if (user.isPresent()) throw new Exception("Email already exist!");
        users.add(new Buyer(
                generateId(),
                name,
                email,
                password,
                dob,
                address,
                phoneNumber
        ));
    }

    public String getName(String userId) {
        return users
                .stream()
                .filter((user) -> user.getId().equals(userId))
                .findFirst()
                .map(User::getName)
                .orElse("404 Not Found");
    }

    public String getEmail(String userId) {
        return users
                .stream()
                .filter((user) -> user.getId().equals(userId))
                .findFirst()
                .map(User::getEmail)
                .orElse("404 Not Found");
    }

    public String getPassword(String userId) {
        return users
                .stream()
                .filter((user) -> user.getId().equals(userId))
                .findFirst()
                .map(User::getPassword)
                .orElse("404 Not Found");
    }

    public void deleteUser(String id) throws Exception {
        Optional<User> user = users
                .stream()
                .filter((b) -> b.getId().equals(id))
                .findFirst();
        if (!user.isPresent()) {
            throw new Exception("Invalid user id!");
        }
        users.remove(user.orElse(null));
    }

    public void editUser(String id, String email, String password) throws Exception {
        Optional<User> user = users
                .stream()
                .filter((b) -> b.getId().equals(id))
                .findFirst();
        if (!user.isPresent()) {
            throw new Exception("Invalid user id!");
        }
        user.orElse(null).setEmail(email);
        user.orElse(null).setPassword(password);
    }

    public Stream<User> listUser() {
        return users.stream();
    }

    private Buyer getBuyer(String userId) throws Exception {
        Optional<User> user = users
                .stream()
                .filter((b) -> b.getId().equals(userId))
                .findFirst();
        if (!user.isPresent()) {
            throw new Exception("Invalid user id!");
        }
        if (!(user.orElse(null) instanceof Buyer)) {
            throw new Exception("User is not a Buyer!");
        }

        return (Buyer) user.orElse(null);
    }

    public Stream<CartItem> getCartItems(String userId) throws Exception {
        Buyer buyer = getBuyer(userId);
        return buyer.getCartItem();
    }


    public void addCartItem(
            String userId,
            String bookId,
            int quantity
    ) throws Exception {
        Buyer buyer = getBuyer(userId);
        buyer.addCartItem(bookId,quantity);
    }

    public void editCartItem(
            String userId,
            String bookId,
            int quantity
    ) throws Exception {
        Buyer buyer = getBuyer(userId);
        buyer.editCartItem(bookId,quantity);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}


