package Menu.User;

import java.time.LocalDate;

public class Buyer extends User{
    private final LocalDate dob;
    private String address;
    private String phoneNumber;

    public Buyer(
          String id,
          String name,
          String email,
          String password,
          LocalDate dob,
          String address,
          String phoneNumber
    ) {
        super(id, name, email, password);
        this.dob = dob;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public UserType getUserType() {
        return UserType.Buyer;
    }
}
