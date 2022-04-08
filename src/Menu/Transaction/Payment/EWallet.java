package Menu.Transaction.Payment;

import Helper.Helper;

public class EWallet implements Payment {
    @Override
    public boolean pay(int price) {
        Helper.printHeader("Paying " + price + " with " + getName());

        String phoneNum = Helper.getString(
              () -> Helper.print("Please input your phone number : "),
              input -> {
                  if (!input.matches("[0-9]+")) {
                      Helper.println("Card number can only contain number!");
                      return true;
                  }
                  return false;
              },
              String::isEmpty
        );
        if (phoneNum.isEmpty()) return false;
        String otp = Helper.getString(
              () -> Helper.print("Please input your otp : "),
              input -> {
                  if (!input.matches("[0-9]+")) {
                      Helper.println("OTP can only contain number!");
                      return true;
                  }
                  if (input.length() != 4) {
                      Helper.println("OTP should be 4 character long!");
                      return true;
                  }
                  return false;
              },
              String::isEmpty
        );
        return !otp.isEmpty();
    }

    @Override
    public String getName() {
        return "E-Wallet";
    }
}
