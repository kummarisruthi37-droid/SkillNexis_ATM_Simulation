import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Account account;
    private ArrayList<Transaction> transactions;
    private Scanner scanner;

    private final String ACCOUNT_FILE = "account.txt";
    private final String TRANSACTION_FILE = "transactions.txt";

    public ATM() {

        scanner = new Scanner(System.in);
        transactions = new ArrayList<Transaction>();

        loadAccount();
        loadTransactions();
    }

    public void start() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("        ATM SIMULATION SYSTEM");
        System.out.println("======================================");

        if (login()) {
            showMenu();
        } else {
            System.out.println();
            System.out.println("Too many incorrect attempts.");
            System.out.println("Account is temporarily locked.");
        }
    }

    private boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter your PIN: ");

            try {

                int enteredPin = scanner.nextInt();

                if (account.authenticatePin(enteredPin)) {

                    System.out.println();
                    System.out.println("Login successful!");
                    System.out.println(
                            "Welcome, " + account.getAccountHolderName() + "!"
                    );

                    return true;

                } else {

                    attempts++;

                    System.out.println("Incorrect PIN.");

                    if (attempts < 3) {
                        System.out.println(
                                "Attempts remaining: " + (3 - attempts)
                        );
                    }
                }

            } catch (Exception e) {

                System.out.println("Please enter numbers only.");
                scanner.nextLine();
            }
        }

        return false;
    }

    private void showMenu() {

        int choice = 0;

        while (choice != 7) {

            System.out.println();
            System.out.println("------------ ATM MENU ------------");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Change PIN");
            System.out.println("6. Account Details");
            System.out.println("7. Exit");
            System.out.println("----------------------------------");

            System.out.print("Enter your choice: ");

            try {

                choice = scanner.nextInt();

                if (choice == 1) {
                    checkBalance();
                } 
                else if (choice == 2) {
                    depositMoney();
                } 
                else if (choice == 3) {
                    withdrawMoney();
                } 
                else if (choice == 4) {
                    showTransactionHistory();
                } 
                else if (choice == 5) {
                    changePin();
                } 
                else if (choice == 6) {
                    showAccountDetails();
                } 
                else if (choice == 7) {

                    saveData();

                    System.out.println();
                    System.out.println(
                            "Thank you for using the ATM!"
                    );

                } 
                else {
                    System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );

                scanner.nextLine();
            }
        }
    }

    private void checkBalance() {

        System.out.println();
        System.out.println("========== BALANCE ==========");
        System.out.printf(
                "Current Balance: Rs. %.2f%n",
                account.getBalance()
        );
    }

    private void depositMoney() {

        System.out.print("Enter amount to deposit: ");

        try {

            double amount = scanner.nextDouble();

            if (amount <= 0) {

                System.out.println(
                        "Amount must be greater than zero."
                );

                return;
            }

            account.deposit(amount);

            Transaction transaction =
                    new Transaction(
                            "Deposit",
                            amount,
                            account.getBalance()
                    );

            transactions.add(transaction);

            saveData();

            System.out.println();
            System.out.println("Deposit successful!");

            System.out.printf(
                    "New Balance: Rs. %.2f%n",
                    account.getBalance()
            );

        } catch (Exception e) {

            System.out.println("Invalid amount.");
            scanner.nextLine();
        }
    }

    private void withdrawMoney() {

        System.out.print("Enter amount to withdraw: ");

        try {

            double amount = scanner.nextDouble();

            if (amount <= 0) {

                System.out.println(
                        "Amount must be greater than zero."
                );

                return;
            }

            if (account.withdraw(amount)) {

                Transaction transaction =
                        new Transaction(
                                "Withdrawal",
                                amount,
                                account.getBalance()
                        );

                transactions.add(transaction);

                saveData();

                System.out.println();
                System.out.println("Withdrawal successful!");

                System.out.printf(
                        "Remaining Balance: Rs. %.2f%n",
                        account.getBalance()
                );

            } else {

                System.out.println();
                System.out.println("Insufficient balance.");
            }

        } catch (Exception e) {

            System.out.println("Invalid amount.");
            scanner.nextLine();
        }
    }

    private void showTransactionHistory() {

        System.out.println();
        System.out.println("========== TRANSACTION HISTORY ==========");

        if (transactions.size() == 0) {

            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {

            System.out.println(transaction);
        }
    }

    private void changePin() {

        System.out.print("Enter current PIN: ");

        try {

            int currentPin = scanner.nextInt();

            if (!account.authenticatePin(currentPin)) {

                System.out.println("Incorrect PIN.");
                return;
            }

            System.out.print("Enter new 4-digit PIN: ");
            int newPin = scanner.nextInt();

            if (newPin < 1000 || newPin > 9999) {

                System.out.println(
                        "PIN must contain exactly 4 digits."
                );

                return;
            }

            System.out.print("Confirm new PIN: ");
            int confirmPin = scanner.nextInt();

            if (newPin != confirmPin) {

                System.out.println(
                        "PIN confirmation does not match."
                );

                return;
            }

            account.changePin(newPin);

            System.out.println();
            System.out.println("PIN changed successfully.");

        } catch (Exception e) {

            System.out.println("Invalid PIN.");
            scanner.nextLine();
        }
    }

    private void showAccountDetails() {

        System.out.println();
        System.out.println("========== ACCOUNT DETAILS ==========");

        System.out.println(
                "Account Number: "
                        + account.getAccountNumber()
        );

        System.out.println(
                "Account Holder: "
                        + account.getAccountHolderName()
        );

        System.out.printf(
                "Balance: Rs. %.2f%n",
                account.getBalance()
        );
    }

    private void saveAccount() {

        try {

            FileWriter fileWriter =
                    new FileWriter(ACCOUNT_FILE);

            BufferedWriter writer =
                    new BufferedWriter(fileWriter);

            writer.write(account.getAccountNumber());
            writer.newLine();

            writer.write(account.getAccountHolderName());
            writer.newLine();

            writer.write(
                    String.valueOf(account.getBalance())
            );

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving account data."
            );
        }
    }

    private void saveTransactions() {

        try {

            FileWriter fileWriter =
                    new FileWriter(TRANSACTION_FILE);

            BufferedWriter writer =
                    new BufferedWriter(fileWriter);

            for (Transaction transaction : transactions) {

                writer.write(transaction.toString());
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving transaction data."
            );
        }
    }

    private void saveData() {

        saveAccount();
        saveTransactions();
    }

    private void loadAccount() {

        try {

            FileReader fileReader =
                    new FileReader(ACCOUNT_FILE);

            BufferedReader reader =
                    new BufferedReader(fileReader);

            String accountNumber = reader.readLine();
            String accountHolderName = reader.readLine();
            String balanceText = reader.readLine();

            double balance =
                    Double.parseDouble(balanceText);

            reader.close();

            account =
                    new Account(
                            accountNumber,
                            accountHolderName,
                            1234,
                            balance
                    );

        } catch (Exception e) {

            account =
                    new Account(
                            "ACC1001",
                            "Sruthi",
                            1234,
                            10000.00
                    );

            saveAccount();
        }
    }

    private void loadTransactions() {

        try {

            FileReader fileReader =
                    new FileReader(TRANSACTION_FILE);

            BufferedReader reader =
                    new BufferedReader(fileReader);

            while (reader.readLine() != null) {

                // Previous transactions are stored in the file.
                // New transactions will be added during this session.
            }

            reader.close();

        } catch (Exception e) {

            // No previous transaction file.
        }
    }
}
