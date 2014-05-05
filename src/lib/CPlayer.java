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
			int playerBet = rand.nextInt(5)+1;;
			if(playerBet == 1){
				finalBet = Bet.FOLD;
				break;
			}
			else if(playerBet == 2){
				if(bet!=0){
					continue;
				}
				finalBet = Bet.CHECK;
				break;
			}
			else if(playerBet == 3){
				if(money<bet){
					continue;
				}
				finalBet = Bet.CALL;
				break;
			}
			else if(playerBet == 4){
				finalBet = Bet.ALLIN;
				break;
			}
			else{
				if((money-bet)<bet){
					continue;
				}
				finalBet = rand.nextInt(money-bet);
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
