package interfaces;
import java.util.*;

public interface IPlayer {

    public void addCard(ICard card);

	public ArrayList<ICard> getHand();
	
	public int getMoney();
	
	public int getBet(int bet);
	
	public void addMoney(int amount);
	
	public void subMoney(int amount);
	
}
