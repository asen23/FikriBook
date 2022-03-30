package Menu.User;

public class Owner extends User {
    public Owner(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public UserType getUserType() {
        return UserType.Owner;
    }
}
