package lib;

import java.util.*;

public class CPlayer extends Player {

	
	private String name;
	public CPlayer(String name){
		this.name = name;
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
				if(money<Math.abs(bet) || bet == 0){
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
				int bigBlind = Dealer.smallBlind*2;
				int betMin = Math.abs(bet)*2>=bigBlind*2-totalBet?Math.abs(bet)*2:bigBlind*2-totalBet;
				/*
				if(bet == 0){
					if(money>=bigBlind){
						finalBet = (rand.nextInt((money-bigBlind)+1)+bigBlind)-totalBet;
					}
					else
						continue;
				}
				*/
				if(money>=betMin){
					do
						finalBet = rand.nextInt((money-betMin)+1)+betMin;
					while(finalBet>money);
				}
				else
					continue;
				
				System.out.println("Computer play: Raise "+finalBet+ " to ("+(finalBet+totalBet)+")");
				totalBet += finalBet;
				break;
			}
		}
		return finalBet;
	}
	
	 public String getName() {
	        return name;
	    }
    
}
