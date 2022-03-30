package Menu.User;

public class Admin extends User {
    private boolean isActive;

    public Admin(String id, String name, String email, String password, boolean isActive) {
        super(id, name, email, password);
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public UserType getUserType() {
        return UserType.Admin;
    }
}
