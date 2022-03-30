import Helper.Helper;
import Menu.Auth.Auth;
import Menu.Menu;

public class Main {
    public static void main(String[] args) {
        Helper.toggleDummy();
        new Main().run();
    }

    public void run() {
        Menu currentMenu;
        do {
            currentMenu = new Auth().run();
            if(currentMenu != null) {
                currentMenu.run();
                continue;
            }
            break;
        } while (true);
    }
}
