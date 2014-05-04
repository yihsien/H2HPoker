package lib;

import java.util.ArrayList;

import enums.Stage;
import interfaces.ICard;
import interfaces.IDeck;
import interfaces.ITableCards;

public class TableCards implements ITableCards {

    private static final int MAX_TABLE_CARDS = 5;
    private ArrayList<ICard> cards;
    private Stage stage;

    public TableCards() {
        stage = Stage.PRE_FLOP;
        cards = new ArrayList<ICard>(MAX_TABLE_CARDS);
    }

    @Override
    public void openFlop(IDeck deck) {
        if (stage != Stage.PRE_FLOP) {
            throw new IllegalStateException("not in PRE_FLOP stage");
        }
        cards.add(deck.dealCard());
        cards.add(deck.dealCard());
        cards.add(deck.dealCard());
    }

    @Override
    public void openTurn(IDeck deck) {
        if (stage != Stage.PRE_TURN) {
            throw new IllegalStateException("not in PRE_TURN stage");
        }
        cards.add(deck.dealCard());
    }

    @Override
    public void openRiver(IDeck deck) {
        if (stage != Stage.PRE_RIVER) {
            throw new IllegalStateException("not in PRE_RIVER stage");
        }
        cards.add(deck.dealCard());
    }

    @Override
    public ArrayList<ICard> getCards() {
        return this.cards;
    }

    @Override
    public void clear() {
        this.cards.clear();
        this.stage = Stage.PRE_FLOP;
    }
}
