package lib;

import interfaces.ICard;
import interfaces.IPlayer;
import java.util.*;

public class HPlayer implements IPlayer {

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
		while(true){
			int playerBet = getChoice(bet);
			if(playerBet == 1){
				finalBet = Bet.FOLD;
				break;
			}
			else if(playerBet == 2){
				if(bet!=0){
					System.out.println("You cannot check, please place another bet");
					continue;
				}
				finalBet = Bet.CHECK;
				break;
			}
			else if(playerBet == 3){
				if(money<bet){
					System.out.println("You don't have enough money, please either fold or all-in");
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
					System.out.println("You don't have enough money, please either fold, call, or all-in");
					continue;
				}
				finalBet = playerBet;
				break;
			}
		}
		return finalBet;
	}
	
	private int getChoice(int currentBet) {
		System.out.println("Fold(1), Check(2), Call(3), All-In(4)" +
				" Raise(0-"+(money-currentBet)+")");
		System.out.print("Please enter you bet: ");
		Scanner reader = new Scanner(System.in);
		return reader.nextInt();
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

}
