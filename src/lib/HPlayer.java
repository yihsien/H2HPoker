package lib;

import interfaces.IBet;
import interfaces.ICard;
import interfaces.IPlayer;
import java.util.*;

public class HPlayer implements IPlayer {

	private int money;
	
	@Override
	public ICard[] getHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMoney() {
		return money;
	}

	@Override
	public int getBet(int bet) {
		return 0;
	}
	
	private int getChoice(int currentBet) {
		if(money<currentBet)
			System.out.println("Fold(1), All-in(5)");
		else if(money==currentBet)
			System.out.println("Fold(1), All-in(5)");
		else if((money-currentBet)<currentBet)
		System.out.println("Fold(1), Check(2), Call(3)," +
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

}
