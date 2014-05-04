package interfaces;

import java.util.Comparator;

import enums.Suit;

public interface ICard extends Comparator<ICard> {

    public Suit getSuit();

    public int getValue();

    public String toString();
}
