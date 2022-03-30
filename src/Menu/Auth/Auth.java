package Menu.Auth;

import java.time.LocalDate;

import Helper.Helper;
import Menu.AdminMenu;
import Menu.Menu;
import Menu.User.Admin;
import Menu.User.User;
import Menu.User.UserManager;

public class Auth {
    private static final UserManager userManager = UserManager.getInstance();
    private User currentUser;

    public Menu run() {
        while (true) {
            if(currentUser != null){
                switch (currentUser.getUserType()) {
                    case Buyer:
                    case Owner:
                    case Admin:
                        return new AdminMenu();
                }
            }
            Helper.println(new String[]{
                  "FikriBook",
                  "=========",
                  "1. Login",
                  "2. Register",
                  "0. Exit",
            });
            int choice = Helper.getInt(() -> Helper.print(">> "));
            switch (choice) {
                case 0:
                    return null;
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
            }
        }
    }

    private void login() {
        while (true) {
            String email = Helper.getString(() -> Helper.print("Email : "));
            String password = Helper.getString(() -> Helper.print("Password : "));
            try {
                currentUser = userManager.login(email, password);
                if(currentUser instanceof Admin) {
                    if(!((Admin) currentUser).isActive()) {
                        currentUser = null;
                        throw new Exception("Admin account is inactive!");
                    }
                }
                Helper.prompt("Successfully logged in!");
                return;
            } catch (Exception e) {
                Helper.println(e.getMessage());
            }
            String choice = Helper.getString(() -> Helper.println("Try again? (y/n)"));
            if(choice.equals("y")) continue;
            break;
        }
    }

    private void register() {
        while (true) {
            String name = Helper.getString(() -> Helper.print("Name : "));
            String email = Helper.getString(() -> Helper.print("Email : "));
            String password = Helper.getString(() -> Helper.print("Password : "));
            LocalDate dob;
            while (true) {
                String dobString = Helper.getString(() -> Helper.print("DOB(yyyy-mm-dd) : "));
                try {
                    dob = LocalDate.parse(dobString);
                } catch (Exception ignored) {
                    Helper.println("Wrong date format!");
                    continue;
                }
                break;
            }
            String address = Helper.getString(() -> Helper.print("Address : "));
            String phoneNumber = Helper.getString(() -> Helper.print("phoneNumber : "));
            try {
                userManager.register(
                      name,
                      email,
                      password,
                      dob,
                      address,
                      phoneNumber
                );
            } catch (Exception e) {
                Helper.println(e.getMessage());
                continue;
            }
            break;
        }
        Helper.prompt("Successfully registered, please login with your new credential...");
    }

}
