package Menu;

import Helper.Helper;

import java.util.Collections;

public class AdminMenu extends Menu {
    private String[] adminMenu;
    private final int currentMenuCount;

    public AdminMenu() {
        super.initMenu();
        currentMenuCount = menuCount - 1;
        initMenu();
        initAdditionalMenu();
    }

    protected AdminMenu(boolean initAdditionalMenu) {
        super.initMenu();
        currentMenuCount = menuCount - 1;
        initMenu();
        if(initAdditionalMenu){
            initAdditionalMenu();
        }
    }

    protected void initMenu() {
        adminMenu = new String[]{
              menuCount++ +". Add Book",
              menuCount++ +". Delete Book",
              menuCount++ +". Edit Book",
              menuCount++ +". List Transaction"
        };
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
        }
        return super.processMenu(choice);
    }

    protected void addBook() {
        String title = Helper.getString(() -> Helper.print("Title :"));
        String isbn = Helper.getString(() -> Helper.print("ISBN :"));
        int page = Helper.getInt(() -> Helper.print("Page :"));
        String author = Helper.getString(() -> Helper.print("Author :"));
        String publisher = Helper.getString(() -> Helper.print("Publisher :"));
        int price = Helper.getInt(() -> Helper.print("Price :"));
        bookManager.addBook(
              title,
              isbn,
              page,
              Collections.singletonList(author),
              publisher,
              price
        );
        Helper.prompt("Added book successfully!");
    }

    private String getBookId(String s) {
        String bookId = Helper.getString(
              () -> Helper.print("Book id to " + s + "(0 to cancel): ")
        );
        if (bookId.equals("0")) {
            Helper.prompt("Canceled " + s + "!");
            return null;
        }
        return bookId;
    }

    protected void deleteBook() {
        listBook();
        String bookId = getBookId("delete");
        try {
            bookManager.deleteBook(bookId);
            Helper.prompt("Successfully deleted book!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }

    protected void editBook() {
        listBook();
        String bookId = getBookId("edit");
        int price = Helper.getInt(() -> Helper.print("Price : "));
        try {
            bookManager.editBook(bookId, price);
            Helper.prompt("Successfully edited book!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }

    protected void listTransaction() {
        transactionManager.listTransaction();
    }
}
