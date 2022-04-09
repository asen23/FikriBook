package Menu;

import Helper.Helper;
import Menu.Book.BookManager;
import Menu.Transaction.TransactionManager;
import Menu.User.UserManager;

public abstract class Menu {
    protected static final UserManager userManager = UserManager.getInstance();

    protected static BookManager bookManager = BookManager.getInstance();
    protected static TransactionManager transactionManager = TransactionManager.getInstance();
    protected String[] menu;
    protected int menuCount = 1;

    public Menu() {
        this(true);
    }

    protected Menu(boolean initAdditionalMenu) {
        menu = new String[]{
              menuCount++ + ". Display book",
              menuCount++ + ". Display book detail",
              menuCount++ + ". View My Profile Data",
              menuCount++ + ". Edit My Profile Data",
        };
        if (initAdditionalMenu) {
            initAdditionalMenu();
        }
    }

    protected void initAdditionalMenu() {
        menu = Helper.concatArray(menu, getMenu());
        menu = Helper.concatArray(menu, new String[]{"0. Logout",});
    }

    protected abstract String getHeaderSuffix();

    public void run() {
        while (true) {
            Helper.cls();
            Helper.printHeader("FikriBook Menu " + getHeaderSuffix());
            Helper.println("To cancel form please press enter");
            Helper.println(menu);
            int choice = Helper.getInt(() -> Helper.print(">> "));
            boolean exit = processMenu(choice);
            if (exit) {
                return;
            }
        }
    }

    protected abstract String[] getMenu();

    protected boolean processMenu(int choice) {
        switch (choice) {
            case 1:
                listBook();
                break;
            case 2:
                bookDetail();
                break;
            case 3:
                myUserDetail();
                break;
            case 4:
                myEditUser();
                break;
            case 0:
                userManager.setCurrentUser(null);
                return true;
        }
        return false;
    }

    protected void listBook() {
        Helper.cls();
        Helper.printHeader("Book List");
        printBookList();
        Helper.prompt();
    }

    protected void printBookList() {
        Helper.printTable(new String[]{"ID", "Title", "ISBN", "Page", "Authors", "Publisher", "Price"}, "=",
                          bookManager.listBook()
                                .map((book) -> new String[]{book.getId(), book.getTitle(), book.getIsbn(),
                                      Integer.toString(book.getPage()), book.getAuthors().get(0), book.getPublisher(),
                                      Integer.toString(book.getPrice()),})
                                .toArray(String[][]::new),
                          new int[]{50, 10, 10, 6, 15, 15, 10}, "| "
        );
    }

    protected void bookDetail() {
        Helper.cls();
        Helper.printHeader("Book Details");
        printBookList();

        String bookId = Helper.getString(
              () -> Helper.print("Input Book ID : "),
              id -> {
                  if(bookManager.isValidBookId(id)) return false;
                  Helper.println("Invalid Book ID");
                  return true;
              },
              String::isEmpty
        );
        if (bookId.isEmpty()) return;

        Helper.println();

        Helper.printHeader(bookManager.getTitle(bookId));
        Helper.println("Book ID : " + bookId);
        Helper.println("ISBN : " + bookManager.getIsbn(bookId));
        Helper.println("Page : " + bookManager.getPage(bookId));
        Helper.println("Authors : " + bookManager.getAuthors(bookId));
        Helper.println("Publisher : " + bookManager.getPublisher(bookId));
        Helper.println("Price : " + bookManager.getPrice(bookId));

        Helper.prompt();
    }

    protected String getCurrentUserId() {
        return userManager.getCurrentUser().getId();
    }

    protected void myUserDetail() {
        Helper.printHeader("User Detail");
        String userId = getCurrentUserId();
        Helper.println("User ID: " + userId);
        Helper.println("Name : " + userManager.getUser(userId).getName());
        Helper.println("Email : " + userManager.getUser(userId).getEmail());

        Helper.prompt();
    }

    protected void myEditUser() {
        Helper.printHeader("Edit User");
        String userId = getCurrentUserId();

        String email = Helper.getString(
              () -> Helper.print("Email (enter to keep current value) : "),
              input -> {
                  if (userManager.isValidEmail(input)) return false;
                  Helper.println("Invalid email format!");
                  return true;
              },
              String::isEmpty
        );
        if (email.isEmpty()) {
            email = userManager.getCurrentUser().getEmail();
        }
        String password = Helper.getString(
              () -> Helper.print("Password (enter to keep current value) : "),
              input -> {
                  String errorMessage = userManager.isValidPassword(input);
                  if(errorMessage.isEmpty()) return false;
                  Helper.println(errorMessage);
                  return true;
              },
              String::isEmpty
        );
        if (password.isEmpty()) {
            password = userManager.getCurrentUser().getEmail();
        }
        try {
            userManager.editUser(userId, email, password);
            Helper.prompt("Successfully edited user!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }

}
