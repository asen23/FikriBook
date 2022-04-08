package Menu.Transaction.Payment;

public class CreditCard extends Card {
    public CreditCard(String bankName) {
        super(bankName);
    }

    @Override
    public String getName() {
        return bankName + " (Credit)";
    }
}
