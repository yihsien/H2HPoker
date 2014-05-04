package lib;

import interfaces.IBet;

public class Bet implements IBet {

	private int value = 0;
	public static final int ALLIN = -2;
	public static final int FOLD = -2;
	public static final int CHECK = -1;
	public static final int CALL = 0;
	
	@Override
	public void updateBet(int amount) {
		value = amount;
	}

	@Override
	public int getBet() {
		return value;
	}

}
