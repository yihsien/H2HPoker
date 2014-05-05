package lib;

import interfaces.ICard;
import interfaces.IPlayer;
import java.util.*;

public class CPlayer implements IPlayer {

	private int money;
	private ArrayList<ICard> hand = new ArrayList<ICard>();
	
	@Override
	public ArrayList<ICard> getHand() {
		return hand;
	}

	@Override
	public int getMoney() {
		return money;
	}

	@Override
	public int getBet(int bet) {
		int finalBet;
		Random rand = new Random();
		while(true){
			int playerBet = rand.nextInt(10)+1;
			if(playerBet == 1){
				finalBet = Bet.FOLD;
				System.out.println("Computer play: Fold");
				break;
			}
			else if(playerBet <= 3){
				if(bet!=0){
					continue;
				}
				finalBet = Bet.CHECK;
				System.out.println("Computer play: Check");
				break;
			}
			else if(playerBet <= 7){
				if(money<bet || bet == 0){
					continue;
				}
				finalBet = Bet.CALL;
				System.out.println("Computer play: Call");
				break;
			}
			else if(playerBet <= 8){
				finalBet = Bet.ALLIN;
				System.out.println("Computer play: ALL-In");
				break;
			}
			else{
				if((money-bet)<bet){
					continue;
				}
				finalBet = rand.nextInt(money-bet);
				System.out.println("Computer play: Raise("+finalBet+")");
				break;
			}
		}
		return finalBet;
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
    public String getName() {
        return "computer";
    }

}
