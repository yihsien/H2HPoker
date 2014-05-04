package interfaces;

public interface IPlayer {
	
	public ICard[] getHand();
	
	public int getMoney();
	
	public int getBet(int bet);
	
	public void addMoney(int amount);
	
	public void subMoney(int amount);
	
}
