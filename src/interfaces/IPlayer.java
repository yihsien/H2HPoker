package interfaces;
import java.util.*;

public interface IPlayer {

    public String getName();
    
    public boolean hasPlacedBet();
    
    public void placeBet(boolean doPlaceBet);

    public void addCard(ICard card);

	public ArrayList<ICard> getHand();
	
	public int getMoney();
	
	public int getBet(int bet);
	
	public void addMoney(int amount);
	
	public void subMoney(int amount);
	
	public void clear();
	
	public void setTotalBet(int amount);
	
	public int getTotalBet();
}
