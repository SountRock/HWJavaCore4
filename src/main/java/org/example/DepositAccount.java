package org.example;

import org.example.Exeptions.IllegalArgumentException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DepositAccount extends Account {
    protected enum TypeAccumulation{
        DAY, MOUNT, YEAR
    }
    private TypeAccumulation type;
    private double percents;
    private LocalDate localDate;
    private ChronoUnit between;

    public static DepositAccount create(int ID, String ownerName, double startMoney, double percents, byte numberType) throws IllegalArgumentException {
        IllegalArgumentException.testArguments("It is not possible to create an deposite account with a negative percents", percents);

        return new DepositAccount(ID, ownerName, startMoney, percents / 100.0, numberType);
    }

    public static DepositAccount create(int ID, String ownerName, double startMoney, double percents) throws IllegalArgumentException {
        return create(ID, ownerName, startMoney, percents, (byte) 2);
    }

    private DepositAccount(int ID, String ownerName, double startMoney, double percents, byte numberType) {
        super(ID, ownerName, startMoney);
        this.percents = percents;
        localDate = LocalDate.now();
        switch (numberType){
            case 1:
                type = TypeAccumulation.DAY;
                between = ChronoUnit.DAYS;
                break;
            case 2:
                type = TypeAccumulation.MOUNT;
                between = ChronoUnit.MONTHS;
                break;
            case 3: type = TypeAccumulation.YEAR;
                between = ChronoUnit.YEARS;
                break;
        }
    }

    /**
     * Рассчитать прирост
     * @param date
     * @return calculate result
     */
    public double calculateAccumulation(LocalDate date){
        double plusPercent = (between.between(localDate, date) * percents);
        return balance() * plusPercent;
    }

    /**
     * Рассчитать и принять прирост
     * @param date
     * @throws IllegalArgumentException
     */
    public void calculateAccumulationAndAccept(LocalDate date) throws IllegalArgumentException {
        double plusPercent = (between.between(localDate, date) * percents);
        pushMoney(balance() * plusPercent);
        localDate = date;
    }

    /**
     * Сброс даты то сегодняшней
     */
    public void resetDate(){
        localDate = LocalDate.now();
    }

    public double getPercents() {
        return percents;
    }

    public TypeAccumulation getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DepositAccount{" + super.toStringJustParameters() +
                ", type=" + type +
                ", percents=" + (percents * 100) + "}";
    }
}
