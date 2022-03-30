package Menu;

import Helper.Helper;

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

    private void deleteBook() {
        listBook();
        String bookId = getBookId("delete");
        try {
            bookManager.deleteBook(bookId);
            Helper.prompt("Successfully deleted book!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }

    private void editBook() {
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

    private void listTransaction() {
        transactionManager.listTransaction();
    }
}
