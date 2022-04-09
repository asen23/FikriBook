package Menu;

import Helper.Helper;
import Menu.Transaction.Payment.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BuyerMenu extends Menu {
    private final String[] buyerMenu;
    private final int currentMenuCount;

    public BuyerMenu() {
        this(true);
    }

    protected BuyerMenu(boolean initAdditionalMenu) {
        super(false);
        currentMenuCount = menuCount - 1;
        buyerMenu = new String[]{
              menuCount++ + ". View Cart",
              menuCount++ + ". Add To Cart",
              menuCount++ + ". Edit Cart",
              menuCount++ + ". Process Cart",
              menuCount++ + ". View Transaction History",
        };
        if (initAdditionalMenu) {
            initAdditionalMenu();
        }
    }

    @Override
    protected String getHeaderSuffix() {
        return "(Buyer)";
    }

    @Override
    protected String[] getMenu() {
        return buyerMenu;
    }

    @Override
    protected boolean processMenu(int choice) {
        switch (choice - currentMenuCount) {
            case 1:
                viewCartItems();
                break;
            case 2:
                addCartItem();
                break;
            case 3:
                editCartItem();
                break;
            case 4:
                processCart();
                break;
            case 5:
                listUserTransactions();
                break;

        }
        return super.processMenu(choice);
    }

    private void printCartItems() {
        String userId = userManager.getCurrentUser().getId();
        try {
            Helper.printTable(
                  new String[]{"Book ID", "Quantity"},
                  "=",
                  userManager.getCartItems(userId)
                        .map(items -> new String[]{
                              items.getBookId(),
                              String.valueOf(items.getQuantity()),
                        })
                        .toArray(String[][]::new),
                  new int[]{50, 20},
                  "| "
            );
        } catch (Exception ignored) {
        }
    }

    private void viewCartItems() {
        Helper.cls();
        Helper.printHeader("Cart Content");
        printCartItems();
        Helper.prompt();
    }

    protected void addCartItem() {
        String userId = userManager.getCurrentUser().getId();
        Helper.cls();
        Helper.printHeader("Add Book to Cart");
        printBookList();
        String bookId = Helper.getString(
              () -> Helper.print("Book ID : "),
              id -> {
                  if (bookManager.isValidId(id)) return false;
                  Helper.println("Invalid book id!");
                  return true;
              },
              String::isEmpty
        );
        if (bookId.isEmpty()) return;
        int quantity = Helper.getInt(
              () -> Helper.print("Quantity (0 to cancel) : "),
              -1,
              input -> {
                  if (input > 0) return false;
                  Helper.println("Quantity should not be less than 1!");
                  return true;
              },
              input -> input == 0
        );
        if (quantity == 0) return;
        try {
            userManager.addCartItem(userId, bookId, quantity);
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
            return;
        }

        Helper.prompt("Added book to cart successfully!");
    }

    private boolean isCartEmpty(String userId) {
        try {
            if (!userManager.getCartItems(userId).findAny().isPresent()) {
                Helper.prompt("Your cart is empty!");
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    protected void editCartItem() {
        String userId = userManager.getCurrentUser().getId();
        Helper.cls();
        Helper.printHeader("Edit Cart");
        if (isCartEmpty(userId)) return;
        printCartItems();

        String bookId = Helper.getString(
              () -> Helper.print("Book ID : "),
              id -> {
                  if (bookManager.isValidId(id)) return false;
                  Helper.println("Invalid book id!");
                  return true;
              },
              String::isEmpty
        );
        if (bookId.isEmpty()) return;
        int quantity = Helper.getInt(
              () -> Helper.print("Quantity (0 to delete, -1 to cancel) : "),
              -2,
              input -> {
                  if (input >= 0) return false;
                  Helper.println("Quantity should not be less than 0!");
                  return true;
              },
              input -> input == -1
        );
        if (quantity == -1) return;
        try {
            userManager.editCartItem(userId, bookId, quantity);
        } catch (Exception ignored) {
        }

        Helper.prompt("Cart Edited successfully!");
    }

    protected void processCart() {
        Helper.cls();
        Helper.printHeader("Process Cart");
        String userId = userManager.getCurrentUser().getId();
        if (isCartEmpty(userId)) return;
        String choice = Helper.getString(
              () -> Helper.print("Do you want to proceed to checkout [y/n] : "),
              s -> !Arrays.asList("y", "n").contains(s)
        );
        Payment payment = null;
        if (choice.equals("n")) {
            Helper.prompt();
            return;
        }
        Helper.printTable(
              new String[]{"No", "Payment"},
              "=",
              new String[][]{
                    new String[]{"1", "Credit Card"},
                    new String[]{"2", "Debit Card"},
                    new String[]{"3", "Ogpay"},
                    new String[]{"4", "Owo"}
              },
              new int[]{5, 20},
              "| "
        );
        int paymentChoice = Helper.getInt(
              () -> Helper.print("Choose your payment method [1 - 4] (0 to cancel) : "),
              0,
              i -> !(i < 5 && i > 0)
        );
        String bankName;
        switch (paymentChoice) {
            case 1:
                bankName = Helper.getString(() -> Helper.print("Enter bank name : "));
                payment = new CreditCard(bankName);
                break;
            case 2:
                bankName = Helper.getString(() -> Helper.print("Enter bank name : "));
                payment = new DebitCard(bankName);
                break;
            case 3:
                payment = new Ogpay();
                break;
            case 4:
                payment = new Owo();
                break;
        }
        int totalPrice = 0;
        try {
            totalPrice = userManager
                  .getCartItems(userId)
                  .map(cart -> bookManager.getBook(cart.getBookId()).getPrice() * cart.getQuantity())
                  .reduce(0, Integer::sum);
        } catch (Exception ignored) {
        }
        boolean paymentSuccess = payment.pay(totalPrice);
        if (paymentSuccess) {
            try {
                transactionManager.processCart(
                      userManager
                            .getCartItems(userId)
                            .collect(Collectors.toList())
                      , userId
                );
                userManager
                      .getCartItems(userId)
                      .forEach(cartItem -> {
                          try {
                              userManager.editCartItem(userId, cartItem.getBookId(), 0);
                          } catch (Exception ignored) {
                          }
                      });
            } catch (Exception ignore) {
            }
            Helper.print("Your payment with " + payment.getName() + " is a success!");
        } else {
            Helper.println("Your payment has failed! Please try again");
        }
        Helper.prompt();
    }

    protected void listUserTransactions() {
        Helper.cls();
        Helper.printHeader("View Transactions History");
        try {
            Helper.printTable(
                  new String[]{"Id", "Time"},
                  "=",
                  transactionManager.getTransaction()
                        .filter(transaction -> transaction
                              .getBuyerId()
                              .equals(userManager.getCurrentUser().getId()))
                        .map(transaction -> new String[]{
                              transaction.getId(),
                              transaction.getDateTime().toString(),
                        })
                        .toArray(String[][]::new),
                  new int[]{50, 50},
                  "| "
            );

        } catch (Exception ignored) {
        }
        String transactionId = Helper.getString(
              () -> Helper.print("Enter ID to see details: ")
        );

        if (transactionId.isEmpty()) {
            return;
        } else {
            try {
                Helper.printTable(
                      new String[]{"Book Id", "Quantity"},
                      "=",
                      transactionManager.getDetails(transactionId)
                            .map(detail -> new String[]{
                                  detail.getBookId(),
                                  String.valueOf(detail.getQuantity())
                            })
                            .toArray(String[][]::new),
                      new int[]{50, 15},
                      "| "
                );

            } catch (Exception e) {
                Helper.println(e.getMessage());
            }
        }
        Helper.prompt();
    }

}
