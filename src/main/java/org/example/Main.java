package org.example;

import org.example.impl.Cashier;

public class Main {
    public static void calculate(Cashier cashier) {
        double sumWithoutDiscount = cashier.getCountGoods() * cashier.getCost();
        System.out.printf("Sum without discount: %.2f%n", sumWithoutDiscount);
        System.out.printf("Sum with discount = %.2f%%:  %.2f%n", cashier.getDiscount(),
                (1 - cashier.getDiscount() / 100) * sumWithoutDiscount);
    }

    public static void main(String[] args) {
        Cashier cashier1 = new Cashier(15, 25.5, 0.75);
        Cashier cashier2 = new Cashier(1200, 15.7, 42.575);
        Cashier cashier3 = new Cashier(31, 375.8, 59.1);
        System.out.println(cashier1);
        calculate(cashier1);
        System.out.println(cashier2);
        calculate(cashier2);
        System.out.println(cashier3);
        calculate(cashier3);
    }
}