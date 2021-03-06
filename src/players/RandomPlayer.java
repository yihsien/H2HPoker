package players;

import java.util.*;

import lib.Bet;
import lib.Dealer;
import lib.Player;

public class RandomPlayer extends Player {

	public RandomPlayer(String name){
	    super(name);
	}

	@Override
	public int getBet(int bet) {
		int finalBet;
		Random rand = new Random();
		while(true){
			int playerBet = rand.nextInt(10)+1;
			if(playerBet == 1){
				finalBet = Bet.FOLD;
				System.out.println(this.getName() + " play: Fold");
				break;
			}
			else if(playerBet <= 3){
				if(bet!=0){
					continue;
				}
				finalBet = Bet.CHECK;
				System.out.println(this.getName() + " play: Check");
				break;
			}
			else if(playerBet <= 7){
				if(money<Math.abs(bet) || bet == 0){
					continue;
				}
				finalBet = Bet.CALL;
				System.out.println(this.getName() + " play: Call");
				break;
			}
			else if(playerBet <= 8){
				finalBet = Bet.ALLIN;
				System.out.println(this.getName() + " play: ALL-In");
				break;
			}
			else{
				int bigBlind = Dealer.getDealerInstance().smallBlind*2;
				int betMin = Math.abs(bet)*2>=bigBlind*2-tempBet?Math.abs(bet)*2:bigBlind*2-tempBet;
				if(money>=betMin){
					do
						finalBet = rand.nextInt((money-betMin)+1)+betMin;
					while(finalBet>money);
				}
				else
					continue;
				
				System.out.println(this.getName() + " play: Raise by " + 
				        finalBet+ " to ("+(finalBet+tempBet)+")");
				tempBet += finalBet;
				totalBet += finalBet;
				break;
			}
		}
		return finalBet;
	}

}
