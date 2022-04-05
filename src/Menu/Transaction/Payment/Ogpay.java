package Menu.Transaction.Payment;

public class Ogpay implements Payment{
    @Override
    public boolean pay(int price) {
        return true;
    }

    @Override
    public String getName() {
        return "Ogpay";
    }
}
