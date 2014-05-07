package lib;

import interfaces.ICard;
import interfaces.IMove;

import interfaces.IPlayer;
import java.util.ArrayList;


public abstract class Player implements IPlayer {
	
	
	protected int money;
	protected int totalBet = 0;
	protected int tempBet = 0;
	protected IMove move = null;
	private ArrayList<ICard> hand = new ArrayList<ICard>();
	private boolean hasPlacedBet = false;
	
	public Player() {
	    this.move = new Move();
	}
	
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
    
    @Override
    public void setTotalBet(int amount){
    	totalBet = amount;
    }
    
    @Override
    public int getTotalBet(){
    	return totalBet;
    }
    
    @Override
    public void setTempBet(int amount){
    	tempBet = amount;
    }
    
    @Override
    public int getTempBet(){
    	return tempBet;
    }

    @Override
    public IMove getMove(){
        return move;
    }

}
