package Menu.Transaction;

import Helper.Helper;
import Menu.Transaction.Cart.CartItem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class TransactionManager {
    private static final TransactionManager transactionManager = new TransactionManager();
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    private TransactionManager() {
        if (Helper.isDummyEnabled()) {
            transactions.add(new Transaction(
                    generateId(),
                    LocalDateTime.of(2022, 3, 1, 10, 10, 10, 100000000),
                    Collections.singletonList(
                            new CartItem("book-2a77cd44-4e85-4018-a21c-8c352f071d56", 1)),
                    "user-fba9b13e-c438-48cc-ad65-670c3d356e40"));
            transactions.add(new Transaction(
                    generateId(),
                    LocalDateTime.of(2022, 3, 2, 2, 2, 2, 20000000),
                    Arrays.asList(
                            new CartItem("book-9fe27cce-29f1-4778-98c7-d3bdcaf5ef1b", 2),
                            new CartItem("book-67e7279c-0a2c-4321-9cca-3eb339dffd08", 1)),
                    "user-adb4ff58-8d7a-45e6-a733-540ea4aa2545"));
        }
    }

    public static TransactionManager getInstance() {
        return transactionManager;
    }

    private String generateId() {
        return Helper.generateId("transaction");
    }

    public Stream<Transaction> getTransaction() {
        return transactions.stream();
    }

    public void processCart(List<CartItem> cartItems, String userId) {
        Transaction transaction = new Transaction(
                generateId(),
                LocalDateTime.now(),
                cartItems,
                userId);
        transactions.add(transaction);
    }

    public Stream<Detail> getDetails(String transactionId) throws Exception {
        return transactions
                .stream()
                .filter((t) -> t.getId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> new Exception("TransactionId invalid"))
                .getDetails();
    }

}
