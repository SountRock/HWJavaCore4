package org.example;

import org.example.Exeptions.IllegalArgumentException;
import org.example.Exeptions.InsufficientFundsException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Account> accountList = new ArrayList<>();
        String command;
        do {
            System.out.println("create - создать аккаунт \n" +
                    "transaction - перевести сумму с одного счета на другой" +
                    "endop - закончить работу программы");
            System.out.println("Enter command:");
            command = scanner.nextLine().toLowerCase().replaceAll(" ", "");

            if(command.equals("create")){
                AccountsWork(accountList);
            }
            if(command.equals("transaction")){
                workWithTransaction(accountList);
            }

        } while (!command.equals("endop"));
    }

    /**
     * Метод создания и работы со всеми типами аккаунтов
     * @param accountList
     */
    public static void AccountsWork(List<Account> accountList){
        String[] parameters;
        System.out.println("which account do you want to create? \n" +
                "(Enter: Account or Credit or Deposit)");
        String choice = scanner.nextLine().toLowerCase().replaceAll(" ", "");
        switch (choice) {
            case "account":
                System.out.println("Enter parameters through space (account ID, owner name, start money):");
                parameters = scanner.nextLine().split(" ");
                accountList.add(workWithAccount(parameters));
                break;
            case "credit":
                System.out.println("Enter parameters through space (account ID, owner name, credit money, removal limit):");
                parameters = scanner.nextLine().split(" ");
                accountList.add(workWithCredit(parameters));
                break;
            case "deposit":
                System.out.println("Enter parameters through space (account ID, owner name, start money, percents (%), number type (1 - DAY, 2 - MONTH, 3 - YEAR)):");
                parameters = scanner.nextLine().split(" ");
                accountList.add(workWithDeposit(parameters));
                break;
        }
    }

    /**
     * Метод создания и работы с Депозитом
     * @param parameters
     * @return new account
     */
    public static DepositAccount workWithDeposit(String[] parameters) {
        showBaseCommand();
        System.out.println("calcAcum - рассчитать прирост денег на депозите+ \n" +
                "calcAccept - рассчитать прирост денег на депозите и Вписать его в текущий баланс");

        DepositAccount account = null;
        try {
            account = DepositAccount.create(
                    Integer.parseInt(parameters[0]), //ID
                    parameters[1], //ownerName
                    Double.parseDouble(parameters[2]), //startMoney
                    Double.parseDouble(parameters[3]), //percents
                    Byte.parseByte(parameters[4]) //numberType
            );

            System.out.println("Enter command:");
            while (true){
                String temp = scanner.nextLine().toLowerCase().replaceAll(" ", "");

                if(temp.equals("endwork")){
                    return account;
                }

                workWithBaseAccountCommand(account, temp);

                String[] readDate;
                LocalDate date;
                try{
                    switch (temp) {
                        case "calcacum":
                            System.out.println("Enter date (YYYY.MM.DD):");
                            readDate = scanner.nextLine().split("\\.");
                            date = LocalDate.of(Integer.parseInt(readDate[0]), Integer.parseInt(readDate[1]), Integer.parseInt(readDate[2]));

                            System.out.println("Accumulation is " + account.calculateAccumulation(date));
                            break;
                        case "calcaccept":
                            System.out.println("Enter date (YYYY.MM.DD):");
                            readDate = scanner.nextLine().split("\\.");
                            date = LocalDate.of(Integer.parseInt(readDate[0]), Integer.parseInt(readDate[1]), Integer.parseInt(readDate[2]));

                            account.calculateAccumulationAndAccept(date);
                            System.out.println(account);
                            break;
                    }
                } catch (DateTimeException e){
                    System.out.println("Data not correct entered");
                }

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Parameters not correct entered");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        return account;
    }

    /**
     * Метод создания и работы с Кредитом
     * @param parameters
     * @return new account
     */
    public static Account workWithCredit(String[] parameters) {
        showBaseCommand();

        Account account = null;
        try {
            account = CreditAccount.create(
                    Integer.parseInt(parameters[0]),
                    parameters[1],
                    Double.parseDouble(parameters[2]),
                    Double.parseDouble(parameters[3])
            );

            System.out.println("Enter command:");
            while (true){
                String temp = scanner.nextLine().toLowerCase().replaceAll(" ", "");

                if(temp.equals("endwork")){
                    return account;
                }

                workWithBaseAccountCommand(account, temp);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Parameters not correct entered");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        return account;
    }

    /**
     * Метод создания и работы с Обычным Аккаунтом
     * @param parameters
     * @return new account
     */
    public static Account workWithAccount(String[] parameters) {
        showBaseCommand();

        Account account = null;
        try {
            account = Account.create(
                    Integer.parseInt(parameters[0]),
                    parameters[1],
                    Double.parseDouble(parameters[2])
            );

            System.out.println("Enter command:");
            while (true){
                String temp = scanner.nextLine().toLowerCase().replaceAll(" ", "");

                if(temp.equals("endwork")){
                    return account;
                }

                workWithBaseAccountCommand(account, temp);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Parameters not correct entered");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        return account;
    }

    /**
     * Метод выполнения базовых операций для всех типов аккаутов
     * @param account
     * @param command
     * @throws IllegalArgumentException
     * @throws InsufficientFundsException
     */
    public static void workWithBaseAccountCommand(Account account, String command) throws IllegalArgumentException, InsufficientFundsException {
        switch (command) {
            case "push":
                System.out.println("Enter sum:");
                account.pushMoney(scanner.nextDouble());
                System.out.println(account);
                break;
            case "pull":
                System.out.println("Enter sum:");
                account.pullMoney(scanner.nextDouble());
                System.out.println(account);
                break;
        }
    }

    /**
     * Показать комманды базовых операций для аккаунтов
     */
    public static void showBaseCommand(){
        System.out.println("endWork - закончить работу со счетом" +
                "push - положить деньги на счет \n" +
                "pull - снять деньги со счета");
    }

    /**
     * Метод обеспечивающий перевод суммы из одного аккаунта (Input account) в другой (Output account)
     * @param accountList
     */
    public static void workWithTransaction(List<Account> accountList){
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println(i + " : " + accountList.get(i));
        }
        try{
            System.out.println("Enter Input account index:");
            int indexIn = scanner.nextInt();
            System.out.println("Enter Output account index:");
            int indexOut = scanner.nextInt();
            System.out.println("Enter sum of money:");
            double sum = scanner.nextInt();
            Transaction.transaction(accountList.get(indexIn), accountList.get(indexOut), sum);
            System.out.println(accountList.get(indexIn));
            System.out.println(accountList.get(indexOut));
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not found account index in list");;
        }

    }
}