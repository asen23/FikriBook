package Menu;

import Helper.Helper;
import Menu.Transaction.Payment.*;
import Menu.Transaction.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                listTransactions();
                break;

        }
        return super.processMenu(choice);
    }

    private void viewCartItems() {
        Helper.cls();
        Helper.printHeader("Cart Content");
        String userId = "user-fba9b13e-c438-48cc-ad65-670c3d356e40";
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
        Helper.prompt();
    }

    protected void addCartItem() {
        String userId = "user-fba9b13e-c438-48cc-ad65-670c3d356e40";
        Helper.cls();
        Helper.printHeader("Add Book to Cart");
        printBookList();
        String bookId = Helper.getString(() -> Helper.print("Book ID: "));
        int quantity = Helper.getInt(() -> Helper.print("Quantity: "));
        try {
            userManager.addCartItem(userId, bookId, quantity);
        } catch (Exception ignored) {
        }

        Helper.prompt("Added book to cart successfully!");
    }

    protected void editCartItem() {
        String userId = "user-fba9b13e-c438-48cc-ad65-670c3d356e40";
        Helper.cls();
        Helper.printHeader("Edit Cart");
        viewCartItems();

        String bookId = Helper.getString(() -> Helper.print("Book ID :"));
        int quantity = Helper.getInt(() -> Helper.print("Quantity :"));
        try {
            userManager.editCartItem(userId, bookId, quantity);
        } catch (Exception ignored) {
        }

        Helper.prompt("Cart Edited successfully!");
    }

    protected void processCart() {
        Helper.cls();
        Helper.printHeader("Process Cart");
        String choice = Helper.getString(() -> Helper.print("Do you want to proceed to checkout [y/n]: "), s -> !Arrays.asList("y", "n").contains(s));
        Payment payment = null;
        if (choice.equals("n")){
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
        int paymentChoice = Helper.getInt(() -> Helper.print("Choose your payment method [1-4]: "), 0, i -> !(i < 5 && i > 0));
        String bankName;
        switch (paymentChoice) {
            case 1:
                bankName = Helper.getString(() -> Helper.print("Enter bank name"));
                payment = new CreditCard(bankName);
                break;
            case 2:
                bankName = Helper.getString(() -> Helper.print("Enter bank name"));
                payment = new DebitCard(bankName);
                break;
            case 3:
                payment = new Ogpay();
                break;
            case 4:
                payment = new Owo();
                break;
        }
        boolean paymentSuccess = payment.pay(1);
        if (paymentSuccess) {
            try {
                String userId = "user-fba9b13e-c438-48cc-ad65-670c3d356e40";
                transactionManager.processCart(userManager.getCartItems(userId).collect(Collectors.toList()), userId);
            } catch (Exception ignore) {
            }
            Helper.print("Your payment with " + payment.getName() + " is a success!");
        } else {
            Helper.println("Your payment has failed! Please try again");
        }
        Helper.prompt();
    }

    protected void listTransactions() {
        Helper.cls();
        Helper.printHeader("List Transactions");
        try {
            Helper.printTable(
                    new String[]{"Id", "Time"},
                    "=",
                    transactionManager.getTransaction()
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
        String transactionId = Helper.getString(() -> Helper.print("Enter ID to see details (Blank to exit): "));

        if (transactionId.isEmpty()) {
            Helper.prompt();
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
