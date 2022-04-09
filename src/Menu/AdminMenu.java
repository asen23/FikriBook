package Menu;

import java.util.Arrays;

import Helper.Helper;
import Menu.User.UserManager;

public class AdminMenu extends Menu {
    protected static final UserManager userManager = UserManager.getInstance();
    private final String[] adminMenu;
    private final int currentMenuCount;

    public AdminMenu() {
        this(true);
    }

    protected AdminMenu(boolean initAdditionalMenu) {
        super(false);
        currentMenuCount = menuCount - 1;
        adminMenu = new String[]{
              menuCount++ + ". Add Book",
              menuCount++ + ". Delete Book",
              menuCount++ + ". Edit Book",
              menuCount++ + ". List Transaction",
              menuCount++ + ". Display User",
              menuCount++ + ". Display User Detail",
              menuCount++ + ". Edit User",
              menuCount++ + ". Delete User"
        };
        if (initAdditionalMenu) {
            initAdditionalMenu();
        }
    }

    @Override
    protected String getHeaderSuffix() {
        return "(Admin)";
    }

    @Override
    protected String[] getMenu() {
        return adminMenu;
    }

    @Override
    protected boolean processMenu(int choice) {
        switch (choice - currentMenuCount) {
            case 1:
                addBook();
                break;
            case 2:
                deleteBook();
                break;
            case 3:
                editBook();
                break;
            case 4:
                listTransaction();
                break;
            case 5:
                listUser();
                break;
            case 6:
                userDetail();
                break;
            case 7:
                editUser();
                break;
            case 8:
                deleteUser();
                break;
        }
        return super.processMenu(choice);
    }

    protected void addBook() {
        Helper.printHeader("Add Book");
        String title = Helper.getString(() -> Helper.print("Title : "));
        if (title.isEmpty()) return;
        String isbn = Helper.getString(
              () -> Helper.print("ISBN : "),
              input -> {
                  if (input.matches("[0-9-]+")) return false;
                  Helper.println("ISBN should only contain number and dash character");
                  return true;
              },
              String::isEmpty
        );
        if (isbn.isEmpty()) return;
        int page = Helper.getInt(
              () -> Helper.print("Page (0 to cancel) : "),
              -1,
              input -> {
                  if (input > 0) return false;
                  Helper.println("Page count should be at least 1!");
                  return true;
              },
              input -> input == 0
        );
        if (page == 0) return;
        String author = Helper.getString(
              () -> Helper.print("Author (comma separated) : "),
              input -> {
                  if (input.contains(" ,") || input.contains(", ")) {
                      Helper.println("Author should be separated by comma only (no whitespace)");
                      return true;
                  }
                  return false;
              },
              String::isEmpty
        );
        if (author.isEmpty()) return;
        String publisher = Helper.getString(() -> Helper.print("Publisher : "));
        if (publisher.isEmpty()) return;
        int price = Helper.getInt(
              () -> Helper.print("Price (0 to cancel) : "),
              -1,
              input -> {
                  if (input > 0) return false;
                  Helper.println("Price should not be lower than 1!");
                  return true;
              },
              input -> input == 0
        );
        if (price == 0) return;
        bookManager.addBook(
              title,
              isbn,
              page,
              Arrays.asList(author.split(",")),
              publisher,
              price
        );
        Helper.prompt("Added book successfully!");
    }

    private String getBookId(String s) {
        return Helper.getString(
              () -> Helper.print("Book id to " + s + " : "),
              id -> {
                  if (bookManager.isValidId(id)) return false;
                  Helper.println("Book Id does not exist!");
                  return true;
              },
              String::isEmpty
        );
    }

    protected void deleteBook() {
        Helper.printHeader("Delete Book");
        printBookList();
        Helper.println();
        String bookId = getBookId("delete");
        if (bookId.isEmpty()) return;
        try {
            bookManager.deleteBook(bookId);
            Helper.prompt("Successfully deleted book!");
        } catch (Exception ignored) {
        }
    }

    protected void editBook() {
        Helper.printHeader("Edit Book");
        printBookList();
        Helper.println();
        String bookId = getBookId("edit");
        if (bookId.isEmpty()) return;
        int price = Helper.getInt(
              () -> Helper.print("Price (0 to keep current value) : "),
              -1,
              input -> {
                  if (input > 0) return false;
                  Helper.println("Price should not be lower than 1!");
                  return true;
              },
              input -> input == 0
        );
        if (price == 0) {
            price = bookManager.getBook(bookId).getPrice();
        }
        try {
            bookManager.editBook(bookId, price);
            Helper.prompt("Successfully edited book!");
        } catch (Exception ignored) {
        }
    }

    protected void listTransaction() {
        Helper.printHeader("Transaction List");
        Helper.printTable(new String[]{"ID", "Date", "Buyer"}, "=",
                          transactionManager.getTransaction()
                                .map(transaction -> new String[]{transaction.getId(), transaction.getDateTime().toString(),
                                      userManager.getUser(transaction.getBuyerId()).getEmail(),})
                                .toArray(String[][]::new),
                          new int[]{50, 20, 30}, "| "
        );
        Helper.prompt();
    }

    protected void printUser() {
        Helper.printTable(
              new String[]{"ID", "Name", "Email"},
              "=",
              userManager.listUser()
                    .map(user -> new String[]{
                          user.getId(),
                          user.getName(),
                          user.getEmail(),
                    })
                    .toArray(String[][]::new),
              new int[]{50, 10, 30},
              "| "
        );
    }

    protected void listUser() {
        Helper.printHeader("User List");
        printUser();
        Helper.prompt();
    }

    protected void userDetail() {
        Helper.printHeader("User Detail");
        printUser();

        String userId = getUserId("Input User ID : ");

        Helper.println();

        Helper.printHeader("Detail");
        Helper.println("User ID: " + userId);
        Helper.println("Name: " + userManager.getUser(userId).getName());
        Helper.println("Email: " + userManager.getUser(userId).getEmail());
        Helper.prompt();
    }

    private String getUserId(String s) {
        return Helper.getString(
              () -> Helper.print(s),
              id -> {
                  if (userManager.isValidId(id)) return false;
                  Helper.println("User Id does not exist!");
                  return true;
              },
              String::isEmpty
        );
    }

    protected void deleteUser() {
        Helper.printHeader("Delete User");
        listUser();
        String userId = getUserId("User id to delete : ");
        if (userId.isEmpty()) return;
        try {
            userManager.deleteUser(userId);
            Helper.prompt("Successfully deleted user!");
        } catch (Exception ignored) {
        }
    }

    protected void editUser() {
        Helper.printHeader("Edit User");
        listUser();
        String userId = getUserId("User id to edit : ");
        if (userId.isEmpty()) return;
        String email = Helper.getString(
              () -> Helper.print("Email : "),
              input -> {
                  if (userManager.isValidEmail(input)) return false;
                  Helper.println("Invalid email format!");
                  return true;
              },
              String::isEmpty
        );
        if (email.isEmpty()) email = userManager.getUser(userId).getEmail();
        String password = Helper.getString(
              () -> Helper.print("Password : "),
              input -> {
                  String errorMessage = userManager.isValidPassword(input);
                  if (errorMessage.isEmpty()) return false;
                  Helper.println(errorMessage);
                  return true;
              },
              String::isEmpty
        );
        if (password.isEmpty()) password = userManager.getUser(userId).getPassword();
        try {
            userManager.editUser(userId, email, password);
            Helper.prompt("Successfully edited user!");
        } catch (Exception ignored) {
        }
    }

}
