package Menu;

import Helper.Helper;

public class AdminMenu extends Menu{
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
        int choice = Helper.getInt(() -> {
            Helper.print(">> ");
        });
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
        
    }

    private void deleteBook() {

    }

    private void editBook() {

    }

    private void listTransaction() {
        transactionManager.listTransaction();
    }
}
