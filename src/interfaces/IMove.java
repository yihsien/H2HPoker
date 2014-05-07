package interfaces;

public interface IMove {

    // amount of money added to the pot
    public int getPlacedBet();
    
    // returns one of possible Bet.FOLD, Bet.ALL_IN, Bet.CALL, Bet.Check
    public int getBetChoice();

    public void setPlacedBet(int bet_value);
    
    public void setBetChoice(int bet_choice);
}
