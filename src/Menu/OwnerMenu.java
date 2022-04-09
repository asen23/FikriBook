package Menu;

import Helper.Helper;
import Menu.User.Admin;

public class OwnerMenu extends AdminMenu {
    private final String[] ownerMenu;
    private final int currentMenuCount;

    public OwnerMenu() {
        this(true);
    }

    protected OwnerMenu(boolean initAdditionalMenu) {
        super(false);
        currentMenuCount = menuCount - 1;
        ownerMenu = Helper.concatArray(
              super.getMenu(),
              new String[]{
                    menuCount++ + ". Add Admin",
                    menuCount++ + ". Delete Admin",
                    menuCount++ + ". Toggle Admin Active Status"
              }
        );
        if(initAdditionalMenu){
            initAdditionalMenu();
        }
    }

    @Override
    protected String getHeaderSuffix() {
        return "(Owner)";
    }

    @Override
    protected String[] getMenu() {
        return ownerMenu;
    }

    @Override
    protected boolean processMenu(int choice) {
        switch (choice - currentMenuCount) {
            case 1:
                addAdmin();
                break;
            case 2:
                deleteAdmin();
                break;
            case 3:
                toggleAdminStatus();
                break;
        }
        return super.processMenu(choice);
    }

    private void addAdmin() {
        while (true) {
            Helper.cls();
            Helper.printHeader("Add Admin");
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
            try {
                userManager.registerAdmin(
                      name,
                      email,
                      password
                );
            } catch (Exception e) {
                Helper.println(e.getMessage());
                continue;
            }
            break;
        }
        Helper.prompt("Successfully added admin!");
    }

    private void printAdminList() {
        Helper.printTable(
              new String[]{"ID", "Name", "Email", "Active"},
              "=",
              userManager.listAdmin()
                    .map(admin -> new String[]{
                          admin.getId(),
                          admin.getName(),
                          admin.getEmail(),
                          String.valueOf(admin.isActive())
                    })
                    .toArray(String[][]::new),
              new int[]{50, 15, 30, 10},
              "| "
        );
    }

    private String getAdminId() {
        printAdminList();

        return Helper.getString(
              () -> Helper.print("Input Admin ID : "),
              id -> {
                  if(userManager.isValidId(id)) return false;
                  Helper.println("Invalid Admin ID");
                  return true;
              },
              String::isEmpty
        );
    }

    private void deleteAdmin() {
        Helper.cls();
        Helper.printHeader("Delete Admin");

        String adminId = getAdminId();
        if (adminId.isEmpty()) return;

        userManager.deleteAdmin(adminId);
        Helper.prompt("Deleted admin successfully");
    }

    private void toggleAdminStatus() {
        Helper.cls();
        Helper.printHeader("Toggle Admin Active Status");

        String adminId = getAdminId();
        if (adminId.isEmpty()) return;

        Admin admin = (Admin)userManager.getUser(adminId);
        admin.setActive(!admin.isActive());
        Helper.prompt("Toggled admin active status successfully");
    }
}
