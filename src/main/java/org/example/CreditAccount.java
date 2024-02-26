package org.example;

import org.example.Exeptions.IllegalArgumentException;
import org.example.Exeptions.InsufficientFundsException;

public class CreditAccount extends Account{
    private double removalLimit;
    private double creditMoney;
    private CreditAccount(int ID, String ownerName, double creditMoney, double removalLimit) {
        super(ID, ownerName, creditMoney);
        this.removalLimit = removalLimit;
        this.creditMoney = creditMoney;
    }

    public static CreditAccount create(int ID, String ownerName, double creditMoney, double removalLimit) throws IllegalArgumentException {
        IllegalArgumentException.testArguments("It is not possible to create an account with withdrawal limit", removalLimit);

        return new CreditAccount(ID, ownerName, creditMoney, removalLimit);
    }

    /**
     * Снять деньги
     * @param sumOfMoney
     * @return снятые деньги
     * @throws InsufficientFundsException
     */
    public double pullMoney(double sumOfMoney) throws InsufficientFundsException {
        InsufficientFundsException.testArguments(
                "Sum Of Removal cannot exceed the Removal Limit", sumOfMoney,
                removalLimit
        );
        return super.pullMoney(sumOfMoney);
    }

    /**
     * Получить долг
     * @return dept
     */
    public double dept(){
        double dept = creditMoney - balance();
        return dept > 0 ? dept : 0.0;
    }

    @Override
    public String toString() {
        return "CreditAccount{" + super.toStringJustParameters() +
                ", removalLimit=" + removalLimit +
                ", creditMoney=" + creditMoney + ", dept=" + dept() + "}";
    }
}
