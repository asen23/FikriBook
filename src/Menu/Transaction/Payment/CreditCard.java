package Menu.Transaction.Payment;

public class CreditCard implements Payment {
    private final String bankName;

    public CreditCard(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean pay(int price) {
        return true;
    }

    @Override
    public String getName() {
        return bankName + " (Credit)";
    }
}
