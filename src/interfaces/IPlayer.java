package interfaces;

public interface IPlayer {

    public void addCard(ICard card);

	public ICard[] getHand();
	
	public int getMoney();
	
	public IBet getBet();
	
}
