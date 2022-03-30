package Menu;

import Helper.Helper;
import Menu.Book.BookManager;
import Menu.Transaction.TransactionManager;

public abstract class Menu {
    protected BookManager bookManager = BookManager.getInstance();
    protected TransactionManager transactionManager = TransactionManager.getInstance();

    public void run() {
        while (true) {
            Helper.println(new String[]{
                  "Menu",
                  "====",
                  "1. Display book",
            });
            Helper.println(getMenu());
            boolean exit = processMenu();
            if(exit) {
                return;
            }
        }
    }

    protected abstract String[] getMenu();

    protected abstract boolean processMenu();

    protected void listBook() {
        bookManager.listBook();
    }
}
