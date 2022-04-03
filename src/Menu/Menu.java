package Menu;

import Helper.Helper;
import Menu.Book.BookManager;
import Menu.Transaction.TransactionManager;

public abstract class Menu {
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
        };
        if (initAdditionalMenu) {
            initAdditionalMenu();
        }
    }

    protected void initAdditionalMenu() {
        menu = Helper.concatArray(menu, getMenu());
        menu = Helper.concatArray(
              menu,
              new String[]{
                    "0. Logout",
              }
        );
    }

    protected abstract String getHeaderSuffix();

    public void run() {
        while (true) {
            Helper.cls();
            Helper.printHeader("FikriBook Menu " + getHeaderSuffix());
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
            case 0:
                return true;
        }
        return false;
    }

    protected void listBook() {
        Helper.cls();
        Helper.printHeader("Book List");
        Helper.printTable(
              new String[]{"ID", "Title", "ISBN", "Page", "Authors", "Publisher", "Price"},
              "=",
              bookManager.listBook()
                    .map((book) -> new String[]{
                          book.getId(),
                          book.getTitle(),
                          book.getIsbn(),
                          Integer.toString(book.getPage()),
                          book.getAuthors().get(0),
                          book.getPublisher(),
                          Integer.toString(book.getPrice()),
                    }).toArray(String[][]::new),
              new int[]{50, 10, 10, 6, 15, 15, 10},
              "| "
        );
        Helper.prompt();
    }
}
