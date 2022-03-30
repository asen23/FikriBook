package Menu.Transaction;

import Helper.Helper;
import Menu.Book.BookManager;
import Menu.Transaction.Cart.CartItem;
import Menu.User.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TransactionManager {
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final BookManager bookManager = BookManager.getInstance();
    private static final UserManager userManager = UserManager.getInstance();
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    private TransactionManager(){
        if(Helper.isDummyEnabled()) {
            transactions.add(new Transaction(
                  generateId(),
                  LocalDateTime.of(2022, 3, 1, 10, 10),
                  Collections.singletonList(new CartItem("dummy1", 1)),
                  "buyer1"
            ));
            transactions.add(new Transaction(
                  generateId(),
                  LocalDateTime.of(2022, 3, 2, 2, 2),
                  Arrays.asList(
                        new CartItem("dummy2", 2),
                        new CartItem("dummy3", 1)
                        ),
                  "buyer2"
            ));
        }
    }

    public static TransactionManager getInstance() {
        return transactionManager;
    }

    private String generateId() {
        return Helper.generateId("transaction");
    }

    private int calculatePrice(String bookId, int quantity) {
        return bookManager.getPrice(bookId) * quantity;
    }

    public void listTransaction() {
        Helper.printTable(
              new String[]{"ID", "Date", "Buyer"},
              "=",
              transactions
                    .stream()
                    .map(transaction -> new String[]{
                          transaction.getId(),
                          transaction.getDateTime().toString(),
                          userManager.getEmailWithId(transaction.getBuyerId()),
                    })
                    .toArray(String[][]::new),
              new int[]{50, 20, 30},
              "| "
        );
    }
}
