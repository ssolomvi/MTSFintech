package org.example.impl;

public class Cashier {
    /*
     * Hello
     * That's multi-line comment
     * */
    private final int countGoods; // count of goods -- количеcтво товаров
    private final double cost; // cost -- cумма товара
    private final double discount; // discount for goods -- cкидка на товар

    /**
     * Constructs an object of type Cashier
     * */
    public Cashier(int countGoods, double cost, double discount) {
        this.countGoods = countGoods;
        this.cost = cost;
        this.discount = discount;
    }

    public int getCountGoods() {
        return countGoods;
    }
    public double getCost() {
        return cost;
    }
    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Cashier: count of goods: " + countGoods +
                "; price per item: " + String.format("%.2f", cost) +
                "; discount: " + String.format("%.2f", discount) + ";";
    }
}
