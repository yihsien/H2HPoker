package lib;


import java.util.*;

public class HPlayer extends Player {
	
	private String name;
	public HPlayer(String name){
		this.name = name;
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
				if(playerBet-totalBet>money){
					System.out.println("you are betting more than you have!");
					continue;
				}
				if(bet == 0 && playerBet<Dealer.smallBlind*2){
					System.out.println("you have to bet at least one big blind ("+Dealer.smallBlind*2+")");
					continue;
				}
				if(playerBet<((totalBet+Math.abs(bet)*2)>=(Dealer.smallBlind*4)?(totalBet+Math.abs(bet)*2):(Dealer.smallBlind*4))){
					System.out.println("you have to at least raise to "+
							((totalBet+Math.abs(bet)*2)>=(Dealer.smallBlind*4)?(totalBet+Math.abs(bet)*2):(Dealer.smallBlind*4)));
					continue;
				}	
				finalBet = playerBet-totalBet;
				totalBet+=finalBet;
				break;
			}
		}
		return finalBet;
	}
	
	private int getChoice(int currentBet) {
		int bigBlind = Dealer.smallBlind*2;
		if(money>=currentBet*2){
			System.out.println("Fold(1), Check(2), Call(3), All-In(4), "+
					"Raise to ("+(totalBet+currentBet*2>=bigBlind*2?totalBet+currentBet*2:bigBlind*2)+"-"+(money+totalBet)+")");
		}
		else
			System.out.println("Fold(1), Check(2), Call(3), All-In(4)");
		System.out.print("Please enter your bet: ");
		Scanner reader = new Scanner(System.in);
		return reader.nextInt();
	}


    @Override
    public String getName() {
        return name;
    }
}
