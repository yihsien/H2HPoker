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
				if(money<Math.abs(bet) || bet == 0){
					System.out.println("You cannot call, please either fold or all-in");
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
				if((money-Math.abs(bet))<Math.abs(bet)){
					System.out.println("You don't have enough money, please either fold, call, or all-in");
					continue;
				}
				if(playerBet<Math.abs(bet)){
					System.out.println("You have to raise at least the amount of opponets bet");
					continue;
				}	
				finalBet = playerBet;
				break;
			}
		}
		return finalBet;
	}
	
	private int getChoice(int currentBet) {
	    System.out.println("Computer Bet: " + currentBet);
		System.out.println("Fold(1), Check(2), Call(3), All-In(4)," +
				" Raise("+currentBet+"-"+(money-currentBet)+")");
		System.out.print("Please enter you bet: ");
		Scanner reader = new Scanner(System.in);
		return reader.nextInt();
	}


    @Override
    public String getName() {
        return name;
    }
}
