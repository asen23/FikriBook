package Menu;

import Helper.Helper;
import Menu.User.UserManager;

import java.util.Collections;

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
              menuCount++ +". Add Book",
              menuCount++ +". Delete Book",
              menuCount++ +". Edit Book",
              menuCount++ +". List Transaction",
              menuCount++ +". Display User",
              menuCount++ +". Display User Detail",
              menuCount++ +". Edit User",
              menuCount++ +". Delete User"
        };
        if(initAdditionalMenu){
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
            	Helper.prompt();
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
        Helper.printTable(
              new String[]{"ID", "Date", "Buyer"},
              "=",
              transactionManager.listTransaction()
                    .map(transaction -> new String[]{
                          transaction.getId(),
                          transaction.getDateTime().toString(),
                          userManager.getEmailWithId(transaction.getBuyerId()),
                    })
                    .toArray(String[][]::new),
              new int[]{50, 20, 30},
              "| "
        );
    }
    
    protected void listUser() {
    	Helper.printTable(
    			new String[] {"ID","Name","Email"},
    			"=",
    			userManager.listUser()
    			.map(user -> new String[] {
    					user.getId(),
    					user.getName(),
    					user.getEmail(),
    			})
    			.toArray(String[][]::new),
    			new int[] {50,10,30},
    			"| "
    			);
    	}
    
    protected void userDetail() {
    	listUser();
    	
    	Helper.print("Input User ID: ");
    	String userId = Helper.getString();
    	
    	Helper.println();
    	
    	Helper.println("Here is the Detail of your User!");
    	Helper.println("User ID: "+userId);
    	Helper.println("Name: "+userManager.getName(userId));
    	Helper.println("Email: "+userManager.getEmail(userId));
    	Helper.println("Password: "+userManager.getPassword(userId));
    	Helper.prompt();
    }
    
    private String getUserId(String s) {
        String userId = Helper.getString(
              () -> Helper.print("User id to " + s + "(0 to cancel): ")
        );
        if (userId.equals("0")) {
            Helper.prompt("Canceled " + s + "!");
            return null;
        }
        return userId;
    }
    
    protected void deleteUser() {
        listUser();
        String userId = getUserId("delete");
        try {
            userManager.deleteUser(userId);
            Helper.prompt("Successfully deleted user!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }
    
    protected void editUser() {
    	listUser();
        String userId = getUserId("edit");
        String email = Helper.getString(() -> Helper.print("Email : "));;
        String password = Helper.getString(() -> Helper.print("Password : "));;
        try {
        	userManager.editUser(userId, email, password);
            Helper.prompt("Successfully edited user!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }
    
}
