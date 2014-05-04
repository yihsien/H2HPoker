package lib;

import interfaces.IPot;

public class Pot implements IPot {

    private int money = 0;

    @Override
    public void addMoney(int money) {
        if (money < 0) {
            throw new IllegalStateException("Money cannot be negative.");
        }
        this.money += money;
    }

    @Override
    public int getMoney() {
        return this.money;
    }

    @Override
    public void clear() {
        this.money = 0;
    }
}
