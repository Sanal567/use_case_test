package com.microsoft.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResursiveAtmWithStreams {
    private long totalAmount = 0L;
    private Map<Long, Long> amountAndQuantity = new TreeMap<>(new SortByAmountDesc());
    private Map<Long, Long> withdrawlDenominations = new TreeMap<>(new SortByAmountDesc());
    private List<Long> availabe$Bills = Arrays.asList(200L, 50L, 20L, 10L, 5L, 1L);
    private int currentDenominationIndex;

    // 20, 10, 5, and 1 dollar bills.
    // to support 50$, 200$
    private ResursiveAtmWithStreams() {
        amountAndQuantity.put(1L, 0L);
        amountAndQuantity.put(5L, 0L);
        amountAndQuantity.put(10L, 0L);
        amountAndQuantity.put(50L, 0L);
        amountAndQuantity.put(200L, 0L);
        amountAndQuantity.put(20L, 0L);
    }

    public static void main(String[] args) {
        ResursiveAtmWithStreams atm = new ResursiveAtmWithStreams();

        Map<Long, Long> depositAmountAndQuantity = new HashMap<>();
        depositAmountAndQuantity.put(10L, -1L);
        atm.deposit(depositAmountAndQuantity);

        depositAmountAndQuantity = new HashMap<>();
        depositAmountAndQuantity.put(20L, 0L);
        depositAmountAndQuantity.put(2L, 0L);
        depositAmountAndQuantity.put(10L, 0L);
        atm.deposit(depositAmountAndQuantity);

        depositAmountAndQuantity = new HashMap<>();
        depositAmountAndQuantity.put(10L, 8L);
        depositAmountAndQuantity.put(5L, 20L);
        atm.deposit(depositAmountAndQuantity);

        depositAmountAndQuantity = new HashMap<>();
        depositAmountAndQuantity.put(20L, 3L);
        depositAmountAndQuantity.put(5L, 18L);
        depositAmountAndQuantity.put(1L, 4L);
        atm.deposit(depositAmountAndQuantity);

        atm.withdraw(75);
        atm.withdraw(253);
        atm.withdraw(0);
        atm.withdraw(-25);

        depositAmountAndQuantity = new HashMap<>();
        depositAmountAndQuantity.put(50L, 45L);
        depositAmountAndQuantity.put(200L, 20L);
        atm.deposit(depositAmountAndQuantity);

        atm.withdraw(100L);
        atm.withdraw(256);
        atm.withdraw(1);
        atm.withdraw(100);
        atm.withdraw(20);

        depositAmountAndQuantity = new HashMap<>();
        depositAmountAndQuantity.put(1L, 100L);
        depositAmountAndQuantity.put(5L, 100L);
        atm.deposit(depositAmountAndQuantity);
        atm.withdraw(19);

    }

    private void deposit(Map<Long, Long> deposit) {
        boolean isAllDenominationsZero = true;

        for (Map.Entry<Long, Long> entry : deposit.entrySet()) {

            if (entry.getValue() < 0) {
                System.out.println("Incorrect deposit amount");
                return;
            } else if (entry.getValue() == 0)
                continue;

            else
                isAllDenominationsZero = false;

        }

        if (isAllDenominationsZero) {
            System.out.println("Deposit amount cannot be zero");
            return;
        }

        else { // update quantities and balance

            deposit.entrySet().stream().forEach(entry -> {
                    amountAndQuantity.put(entry.getKey(), amountAndQuantity.get(entry.getKey()) + entry.getValue());
                totalAmount += entry.getKey() * entry.getValue();
            });
        }

        printAvailableQuntitiesInAtm();
    }

    private void withdraw(long withdrawlAmount) {
        if (withdrawlAmount == 0 || withdrawlAmount < 0 || totalAmount < withdrawlAmount)
            System.out.println("Incorrect or insufficient funds");

        else
            withdrawRecursion(withdrawlAmount, availabe$Bills.get(currentDenominationIndex++));
    }

    private void withdrawRecursion(Long withdrawlAmount, Long denminations) {
        long withDrawlAmountInHiger$s = withdrawlAmount / denminations;
        long availabe$sQuantityInATM = amountAndQuantity.get(denminations);

        if (withDrawlAmountInHiger$s > 0 && availabe$sQuantityInATM > 0) {
            long remainingAmount = 0;

            if (availabe$sQuantityInATM >= withDrawlAmountInHiger$s) {
                withdrawDenomination(denminations, withDrawlAmountInHiger$s);
                remainingAmount = withdrawlAmount % denminations;

            } else {
                withdrawDenomination(denminations, availabe$sQuantityInATM);
                remainingAmount = withdrawlAmount - availabe$sQuantityInATM * denminations;
            }
            if (remainingAmount > 0)
                withdrawRecursion(remainingAmount, availabe$Bills.get(currentDenominationIndex++));
            else
                printWithdrawlDenominations();

        } else if (denminations == 1) {
            System.out.println("No possible quantities to dispense specified amount");
            withdrawlDenominations = new TreeMap<>(new SortByAmountDesc());
            currentDenominationIndex = 0;
        } else
            withdrawRecursion(withdrawlAmount, availabe$Bills.get(currentDenominationIndex++));
    }

    private void withdrawDenomination(Long denminations, Long withDrawlAmountInDenomination$s) {
        amountAndQuantity.put(denminations, amountAndQuantity.get(denminations) - withDrawlAmountInDenomination$s);

        withdrawlDenominations.put(denminations, withDrawlAmountInDenomination$s);
        totalAmount -= denminations * withDrawlAmountInDenomination$s;
    }

    private void printWithdrawlDenominations() {
        System.out.print("Dispensed: ");

        withdrawlDenominations.entrySet().stream().forEach(entry -> {
            System.out.print(entry.getKey() + "s=" + entry.getValue() + ",");
        });

        System.out.println();
        printAvailableQuntitiesInAtm();

        withdrawlDenominations = new TreeMap<>(new SortByAmountDesc());
        currentDenominationIndex = 0;
    }

    private void printAvailableQuntitiesInAtm() {
        System.out.print("Balance : ");

        amountAndQuantity.entrySet().stream().forEach(entry -> {
            System.out.print(entry.getKey() + "s=" + entry.getValue() + ",");
        });

        System.out.print("Total =" + totalAmount);
        System.out.println();
    }

}

