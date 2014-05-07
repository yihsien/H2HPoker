package interfaces;

import interfaces.IPot;

public interface IDealer {

    public IPot getPot();
    
    public ITableCards getTableCards();
    
    public IBet getBet();
    
    public IMove getOtherPlayerMove(IPlayer player);
}
