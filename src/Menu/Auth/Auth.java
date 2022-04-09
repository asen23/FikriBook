package Menu.Auth;

import java.time.LocalDate;

import Helper.Helper;
import Menu.AdminMenu;
import Menu.BuyerMenu;
import Menu.OwnerMenu;
import Menu.Menu;
import Menu.User.Admin;
import Menu.User.UserManager;

public class Auth {
    private static final UserManager userManager = UserManager.getInstance();

    public Menu run() {
        while (true) {
            if (userManager.getCurrentUser() != null) {
                switch (userManager.getCurrentUser().getUserType()) {
                    case Buyer:
                        return new BuyerMenu();
                    case Owner:
                        return new OwnerMenu();
                    case Admin:
                        return new AdminMenu();
                }
            }
            Helper.cls();
            Helper.printHeader("FikriBook");
            Helper.println(new String[]{
                  "1. Login",
                  "2. Register",
                  "0. Exit",
            });
            int choice = Helper.getInt(
                  () -> Helper.print(">> "),
                  -1,
                  input -> {
                      if (input >= 0 && input <= 2) return false;
                      Helper.println("Please choose number from available menu!");
                      return true;
                  }
            );
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
            Helper.cls();
            Helper.printHeader("Login");
            String email = Helper.getString(() -> Helper.print("Email : "));
            if (email.isEmpty()) return;
            String password = Helper.getString(() -> Helper.print("Password : "));
            if (password.isEmpty()) return;
            try {
                userManager.setCurrentUser(userManager.login(email, password));
                if (userManager.getCurrentUser() instanceof Admin) {
                    if (!((Admin) userManager.getCurrentUser()).isActive()) {
                        userManager.setCurrentUser(null);
                        throw new Exception("Admin account is inactive!");
                    }
                }
                Helper.prompt("Successfully logged in!");
                return;
            } catch (Exception e) {
                Helper.println(e.getMessage());
            }
            String choice = Helper.getString(() -> Helper.println("Try again? (y/n)"));
            if (choice.equals("y")) continue;
            break;
        }
    }

    private void register() {
        while (true) {
            Helper.cls();
            Helper.printHeader("Register");
            String name = Helper.getString(() -> Helper.print("Name : "));
            if (name.isEmpty()) return;
            String email = Helper.getString(
                  () -> Helper.print("Email : "),
                  input -> {
                      if (userManager.isValidEmail(input)) return false;
                      Helper.println("Invalid email format!");
                      return true;
                  },
                  String::isEmpty
            );
            if (email.isEmpty()) return;
            String password = Helper.getString(
                  () -> Helper.print("Password : "),
                  input -> {
                      String errorMessage = userManager.isValidPassword(input);
                      if (errorMessage.isEmpty()) return false;
                      Helper.println(errorMessage);
                      return true;
                  },
                  String::isEmpty
            );
            if (password.isEmpty()) return;
            LocalDate dob;
            while (true) {
                String dobString = Helper.getString(() -> Helper.print("DOB(yyyy-mm-dd) : "));
                if (dobString.isEmpty()) return;
                try {
                    dob = LocalDate.parse(dobString);
                } catch (Exception ignored) {
                    Helper.println("Wrong date format!");
                    continue;
                }
                break;
            }
            String address = Helper.getString(() -> Helper.print("Address : "));
            if (address.isEmpty()) return;
            String phoneNumber = Helper.getString(
                  () -> Helper.print("Phone Number : "),
                  input -> {
                      if (input.matches("[0-9]+")) return false;
                      Helper.println("Phone Number should only contain number");
                      return true;
                  },
                  String::isEmpty
            );
            if (phoneNumber.isEmpty()) return;
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
