package Menu.Transaction.Payment;

public class DebitCard extends Card {
    public DebitCard(String bankName) {
        super(bankName);
    }

    @Override
    public String getName() {
        return bankName + " (Debit)";
    }
}