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
              menuCount++ +". View My Profile Data",
              menuCount++ +". Edit My Profile Data",
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
                Helper.prompt();
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
    }
    
    protected void bookDetail() {
    	Helper.print("Input Book ID: ");
    	String bookId = Helper.getString();
    	
    	Helper.println();
    	
    	Helper.println("Here is the Detail of your book!");
    	Helper.println("Book ID: "+bookId);
    	Helper.println("Title: "+bookManager.getTitle(bookId));
    	Helper.println("ISBN: "+bookManager.getIsbn(bookId));
    	Helper.println("Page: "+bookManager.getPage(bookId));
    	Helper.println("Authors: "+bookManager.getAuthors(bookId));
    	Helper.println("Publisher: "+bookManager.getPublisher(bookId));
    	Helper.println("Price: "+bookManager.getPrice(bookId));
    	
    	Helper.prompt();
    }
    
    protected void myUserDetail() {
    	String userId="";
    	//get dengan cara apapun itu
    	
    	Helper.println("Here is the Detail of your Profile!");
    	Helper.println("User ID: "+userId);
    	Helper.println("Name: "+userManager.getName(userId));
    	Helper.println("Email: "+userManager.getEmail(userId));
    }
    
    
    protected void myEditUser() {
    	myUserDetail();
    	String userId="";
//        String userId = getUserId("edit");
        String email = Helper.getString(() -> Helper.print("Email : "));;
        String password = Helper.getString(() -> Helper.print("Password : "));;
        try {
        	userManager.editUser(userId, email, password);
            Helper.prompt("Successfully edited book!");
        } catch (Exception e) {
            Helper.prompt(e.getMessage());
        }
    }
}
