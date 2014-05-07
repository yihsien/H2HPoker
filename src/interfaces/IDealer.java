package interfaces;

import interfaces.IPot;

public interface IDealer {

    public void setPlayer1(IPlayer player);

    public void setPlayer2(IPlayer player);

    public IPot getPot();
    
    public ITableCards getTableCards();
    
    public IBet getBet();
    
    public IMove getOtherPlayerMove(IPlayer player);
}
