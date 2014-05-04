package lib;

import interfaces.ICard;
import interfaces.IPlayer;
import java.util.*;

public class CPlayer implements IPlayer {

	private int money;
	
	@Override
	public ArrayList<ICard> getHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMoney() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBet(int bet) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void addMoney(int amount) { 
		money += amount;
	}
	
	public void subMoney(int amount) {
		money -= amount;
	}

    @Override
    public void addCard(ICard card) {
        
    }

}
