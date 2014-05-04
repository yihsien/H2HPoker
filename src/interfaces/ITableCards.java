package interfaces;

import java.util.ArrayList;

public interface ITableCards {

    public void openFlop(IDeck deck);

    public void openTurn(IDeck deck);

    public void openRiver(IDeck deck);

    public ArrayList<ICard> getCards();
}
