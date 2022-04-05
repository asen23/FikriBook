package Menu.Transaction.Payment;

public class DebitCard implements Payment {
    private final String bankName;

    public DebitCard(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean pay(int price) {
        return true;
    }

    @Override
    public String getName() {
        return bankName + " (Debit)";
    }
}
