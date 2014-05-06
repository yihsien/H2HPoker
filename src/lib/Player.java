package lib;

import interfaces.ICard;

import interfaces.IPlayer;
import java.util.ArrayList;


public abstract class Player implements IPlayer {
	
	
	protected int money;
	private ArrayList<ICard> hand = new ArrayList<ICard>();
	private boolean hasPlacedBet = false;
	
	@Override
	public ArrayList<ICard> getHand() {
		return hand;
	}

	@Override
	public int getMoney() {
		return money;
	}

	
	public void addMoney(int amount) { 
		money += amount;
	}
	
	public void subMoney(int amount) {
		money -= amount;
	}

    @Override
    public void addCard(ICard card) {
        hand.add(card);
    }


    @Override
    public void clear() {
        hand = new ArrayList<ICard>();
    }
    


    @Override
    public boolean hasPlacedBet() {
        return this.hasPlacedBet;
    }

    @Override
    public void placeBet(boolean doPlaceBet) {
        this.hasPlacedBet = doPlaceBet;
    }

}
