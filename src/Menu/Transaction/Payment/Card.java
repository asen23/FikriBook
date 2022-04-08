package Menu.Transaction.Payment;

import Helper.Helper;

public class Card implements Payment {
    protected final String bankName;

    public Card(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean pay(int price) {
        Helper.printHeader("Paying " + price + " with " + getName());

        String cardNum = Helper.getString(
              () -> Helper.print("Please input your card number : "),
              input -> {
                  if (!input.matches("[0-9]+")) {
                      Helper.println("Card number can only contain number!");
                      return true;
                  }
                  if (input.length() != 16) {
                      Helper.println("Card number should be 16 character long!");
                      return true;
                  }
                  return false;
              },
              String::isEmpty
        );
        if (cardNum.isEmpty()) return false;
        String pin = Helper.getString(
              () -> Helper.print("Please input your pin : "),
              input -> {
                  if (!input.matches("[0-9]+")) {
                      Helper.println("Pin can only contain number!");
                      return true;
                  }
                  if (input.length() != 6) {
                      Helper.println("Pin should be 6 character long!");
                      return true;
                  }
                  return false;
              },
              String::isEmpty
        );
        return !pin.isEmpty();
    }

    @Override
    public String getName() {
        return bankName;
    }
}
