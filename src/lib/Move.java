package lib;

import interfaces.IMove;

public class Move implements IMove {

    private int betValue;
    private int betChoice;
    
    public Move() {
        this.betValue = 0;
        this.betChoice = -1;
    }

    @Override
    public int getPlacedBet() {
        return this.betValue;
    }

    @Override
    public int getBetChoice() {
        return this.betChoice;
    }

    @Override
    public void setPlacedBet(int bet_value) {
        this.betValue = bet_value;
    }

    @Override
    public void setBetChoice(int bet_choice) {
        this.betChoice = bet_choice;
    }

}
