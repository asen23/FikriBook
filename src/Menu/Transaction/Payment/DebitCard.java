package Menu.Transaction.Payment;

import Helper.Helper;

public class DebitCard implements Payment {
    private final String bankName;
    private final int pin;

    public DebitCard(String bankName, int pin) {
        this.bankName = bankName;
        this.pin = pin;
    }

    @Override
    public boolean pay(int price) {
        Helper.printHeader("Debit Card Payment " + "(" + bankName + ")");
        Helper.println("Total payment: " + price);
        int pin = Helper.getInt(() -> Helper.print("Please input your pin: "));
        if (pin != this.pin) {
            Helper.println("You entered the wrong pin, please try again");
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return bankName + " (Debit)";
    }
}
