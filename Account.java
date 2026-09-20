public class Account {

    private String accountNumber;
    private String accountHolderName;
    private int pin;
    private double balance;

    public Account(String accountNumber, String accountHolderName, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean authenticatePin(int enteredPin) {
        return pin == enteredPin;
    }

    public void changePin(int newPin) {
        pin = newPin;
    }

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public boolean withdraw(double amount) {

        if (amount > balance) {
            return false;
        }

        balance = balance - amount;
        return true;
    }
}