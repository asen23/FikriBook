package Menu;

import Helper.Helper;

public class OwnerMenu extends AdminMenu {
    private final String[] ownerMenu;
    private final int currentMenuCount;

    public OwnerMenu() {
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
        super.initAdditionalMenu();
    }

    @Override
    protected String[] getMenu() {
        return ownerMenu;
    }

    @Override
    protected boolean processMenu(int choice) {
        switch (choice - currentMenuCount) {
            case 1:
                Helper.println("1");
                break;
            case 2:
                Helper.println("2");
                break;
            case 3:
                Helper.println("3");
                break;
        }
        return super.processMenu(choice);
    }
}
