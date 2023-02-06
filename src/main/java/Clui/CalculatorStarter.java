package Clui;

import busniss_logic.*;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CalculatorStarter {

    //private static busniss_logic.ExchangeCalculator ExchangeCalculator;
    private static ExchangeCalculator businesslogic = new BarcenaysCalculator();

    //public static void printValidCurrencies(ExchangeCalculator calculator)
    public static void printValidCurrencies(ExchangeCalculator calculator) {
        System.out.println("Valid currencies with their codes are listed below.");
        int i = 1;
        for (String name : calculator.getCurrencyLongNames()) {
            if (i%4 == 0)
                System.out.println();
            System.out.printf("%-30s", name);
            i++;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        input.useLocale(Locale.ENGLISH);

        System.out.println("\nWelcome to BARCENAYS CAPITAL. This is our "
                + "Currency Exchange Calculator Service.");
        System.out.println("We will offer you the best exchange rates "
                + "at a very moderate commission fee.");

        String origCurrency = "";
        double origAmount = 0.0;
        String endCurrency = "";

        boolean waiting = true;
        while (waiting) {
            try {
                System.out.println("\nPlease indicate the currency that you intend to exchange "
                        + "(international 3 letter code):");
                origCurrency = input.next();
                Currency.valueOf(origCurrency);
                waiting = false;

            } catch (IllegalArgumentException e) {
                System.out.printf("\"%s\" could not be recognized as a known code.\n\n", origCurrency);
                printValidCurrencies(businesslogic);
            }
        }

        waiting = true;
        while (waiting) {
            try {
                System.out.printf("How many %s do you plan to exchange?:%n", origCurrency);
                origAmount = input.nextDouble();
                waiting = false;
            } catch (InputMismatchException e) {
                System.out.print("Please introduce a valid amount. ");
                input.nextLine();    // to clear the buffer
            }
        }

        waiting = true;
        while (waiting) {
            try {
                System.out.printf("Please indicate the currency to which you want to exchange "
                        + "your %s %.2f (international 3 letter code):\n", origCurrency, origAmount);
                endCurrency = input.next();
                Currency.valueOf(endCurrency);
                waiting = false;

            } catch (IllegalArgumentException e) {
                System.out.printf("\"%s\" could not be recognized as a known code.\n\n", origCurrency);
                printValidCurrencies(businesslogic);
            }
        }

        //ForexOperator operator = new ForexOperator(origCurrency, origAmount, endCurrency);
        try {
            //double endAmount = operator.getChangeValue();
            double endAmount = businesslogic.getChangeValue(origCurrency, origAmount, endCurrency);
            //CommissionCalculator calculator = new CommissionCalculator(endAmount,endCurrency);
            //endAmount -= calculator.calculateCommission();
            endAmount -= businesslogic.calculateComission(endAmount,endCurrency);
            System.out.printf("\nYou can obtain a net exchange value of %s %.2f.%n", endCurrency, endAmount);
            System.out.println("You can make it effective at any BARCENAYS CAPITAL office.");
        } catch (Exception e1) {
            System.out.println("\nExcuse us, the conversion could not be done. Please try it a bit later.");
        }

        input.close();
    }

}
