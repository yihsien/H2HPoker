package interfaces;

public interface IDeck {

    public void shuffle();
    
    public int numCardsLeft();
    
    public ICard dealCard();
}
