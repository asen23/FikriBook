package Menu.Transaction.Payment;

public class Owo implements Payment{
    @Override
    public boolean pay(int price) {
        return true;
    }

    @Override
    public String getName() {
        return "Owo";
    }
}
