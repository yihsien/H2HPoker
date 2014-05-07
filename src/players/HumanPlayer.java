package players;


import java.util.*;

import lib.Bet;
import lib.Dealer;
import lib.Player;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name){
	    super(name);
	}
	
	@Override
	public int getBet(int bet) {
		int finalBet;
		while(true){
			int playerBet = getChoice(Math.abs(bet));
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
				if(money<Math.abs(bet) || bet == 0){
					System.out.println("You cannot call, please either fold or check or raise");
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
			    int small_blind = Dealer.getDealerInstance().smallBlind;
				if(playerBet-tempBet>money){
					System.out.println("you are betting more than you have!");
					continue;
				}
				if(bet == 0 && playerBet < small_blind * 2){
					System.out.println("you have to bet at least one big blind ("+small_blind*2+")");
					continue;
				}
				if(playerBet<((tempBet+Math.abs(bet)*2)>=(small_blind*4)?(tempBet+Math.abs(bet)*2):(small_blind*4))){
					System.out.println("you have to at least raise to "+
							((tempBet+Math.abs(bet)*2)>=(small_blind*4)?(tempBet+Math.abs(bet)*2):(small_blind*4)));
					continue;
				}	
				finalBet = playerBet-tempBet;
				tempBet+=finalBet;
				totalBet+=finalBet;
				break;
			}
		}
		return finalBet;
	}
	
	private int getChoice(int currentBet) {
		int bigBlind = Dealer.getDealerInstance().smallBlind*2;
		if(money>=currentBet*2){
			System.out.println("Fold(1), Check(2), Call(3), All-In(4), "+
					"Raise to ("+(tempBet+currentBet*2>=bigBlind*2?tempBet+currentBet*2:bigBlind*2)+"-"+(money+tempBet)+")");
		}
		else
			System.out.println("Fold(1), Check(2), Call(3), All-In(4)");
		System.out.print("Please enter your bet: ");
		@SuppressWarnings("resource")
        Scanner reader = new Scanner(System.in);
		return reader.nextInt();
	}

}
