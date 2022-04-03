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
              menuCount++ +". View Cart",
              menuCount++ +". Add To Cart",
              menuCount++ +". Process Cart",
              menuCount++ +". View Transaction History",
        };
        if(initAdditionalMenu){
            initAdditionalMenu();
        }
    }

    @Override
    protected String getHeaderSuffix() {
        return "(Buyer)";
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
