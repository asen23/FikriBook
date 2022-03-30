package Menu;

import Helper.Helper;

import java.util.Arrays;
import java.util.Collections;

public class AdminMenu extends Menu {
    @Override
    protected String[] getMenu() {
        return new String[]{
              "2. Add Book",
              "3. Delete Book",
              "4. Edit Book",
              "5. List Transaction"
        };
    }

    @Override
    protected boolean processMenu() {
        int choice = Helper.getInt(() -> Helper.print(">> "));
        switch (choice) {
            case 1:
                listBook();
                break;
            case 2:
                addBook();
                break;
            case 3:
                deleteBook();
                break;
            case 4:
                editBook();
                break;
            case 5:
                listTransaction();
                break;

        }
        return false;
    }

    private void addBook() {
        String title = Helper.getString(() -> Helper.println("Title :"));
        String isbn = Helper.getString(() -> Helper.println("ISBN :"));
        int page = Helper.getInt(() -> Helper.println("Page :"));
        String author = Helper.getString(() -> Helper.println("Author :"));
        String publisher = Helper.getString(() -> Helper.println("Publisher :"));
        int price = Helper.getInt(() -> Helper.println("Price :"));
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

    private void deleteBook() {

    }

    private void editBook() {

    }

    private void listTransaction() {
        transactionManager.listTransaction();
    }
}
