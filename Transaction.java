import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private double amount;
    private double balance;
    private String dateTime;

    public Transaction(String type, double amount, double balance) {

        this.type = type;
        this.amount = amount;
        this.balance = balance;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        dateTime = LocalDateTime.now().format(formatter);
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String toString() {

        return dateTime
                + " | "
                + type
                + " | Amount: "
                + amount
                + " | Balance: "
                + balance;
    }
}
