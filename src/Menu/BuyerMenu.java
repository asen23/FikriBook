package Menu;

import Helper.Helper;

public class BuyerMenu extends Menu{
    private final String[] buyerMenu;
    private final int currentMenuCount;

    public BuyerMenu() {
        this(true);
    }

    protected BuyerMenu(boolean initAdditionalMenu) {
        super(false);
        currentMenuCount = menuCount - 1;
        buyerMenu = new String[]{
              "2. View Cart",
              "3. Add To Cart",
              "4. Process Cart",
              "5. View Transaction History",
        };
        if(initAdditionalMenu){
            initAdditionalMenu();
        }
    }

    @Override
    protected String[] getMenu() {
        return buyerMenu;
    }

    @Override
    protected boolean processMenu(int choice) {
        switch (choice - currentMenuCount) {
            case 1:
            case 2:
            case 3:
        }
        return super.processMenu(choice);
    }
}
