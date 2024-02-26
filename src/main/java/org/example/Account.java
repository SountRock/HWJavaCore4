package org.example;

import org.example.Exeptions.IllegalArgumentException;
import org.example.Exeptions.InsufficientFundsException;

public class Account {
    private int ID;
    //private static Set<Integer> IDSet = new HashSet<>();
    private String ownerName;
    private double money;

    /**
     * Метод создания Акаунта
     * @param ID
     * @param ownerName
     * @param startMoney
     * @return new Account
     * @throws IllegalArgumentException
     */
    public static Account create(int ID, String ownerName, double startMoney) throws IllegalArgumentException {
        IllegalArgumentException.testArguments("It is not possible to create an account with a negative balance", startMoney);

        return new Account(ID, ownerName, startMoney);
    }

    /**
     * Метод создания Акаунта
     * @param ID
     * @param ownerName
     * @return new Account
     * @throws IllegalArgumentException
     */
    public static Account create(int ID, String ownerName) throws IllegalArgumentException {
        return create(ID, ownerName, 0.0);
    }

    protected Account(int ID, String ownerName, double startMoney){
        this.ID = ID;
        this.ownerName = ownerName;
        money = startMoney;
    }

    /**
     * Получить текущий баланс
     * @return текущий баланс
     */
    public double balance(){
        return money;
    }

    /**
     * Снять деньги
     * @param sumOfMoney
     * @return снятые деньги
     * @throws InsufficientFundsException
     */
    public double pullMoney(double sumOfMoney) throws InsufficientFundsException {
        if(money == 0.0){
            throw new InsufficientFundsException("balance is empty");
        }

        double rest = money - sumOfMoney;
        InsufficientFundsException.testArguments(
                "Sum Of Removal cannot exceed the Account Balance", 0,
                rest
        );
        money = rest;

        return sumOfMoney;
    }

    /*
    protected double pullAll() {
        if(status.equals(Status.EMPTY)){
            return 0.0;
        }

        double sumOfMoney = status.getMoney();
        status = Status.setMoney(0.0);
        return sumOfMoney;
    }
     */

    public void pushMoney(double sumOfMoney) throws IllegalArgumentException {
        IllegalArgumentException.testArguments("sum cannot negative value", sumOfMoney);

        money += sumOfMoney;
    }

    /**
     * Получить ID клиента
     * @return ID клиента
     */
    public int getID() {
        return ID;
    }

    /**
     * Получить имя владельца
     * @return имя владельца
     */
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ID=" + ID +
                ", ownerName=" + ownerName +
                ", money=" + money +
                '}';
    }

    protected String toStringJustParameters(){
        return "ID=" + ID + ", ownerName=" + ownerName + ", balance=" + money;
    }
}
