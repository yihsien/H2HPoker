package lib;

import enums.Suit;
import interfaces.ICard;

public class Card implements ICard, Comparable<ICard> {

    /**
     * This card's suit, one of the constants SPADES, HEARTS, DIAMONDS,
     * or CLUBS.  The suit cannot be changed after the card is
     * constructed.
     */
    private final Suit suit; 
    
    /**
     * The card's value.  For a normal card, this is one of the values
     * 2 through 14, with 14 representing ACE. The value cannot be changed after
     * the card is constructed.
     */
    private final int value;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }
    
    @Override
    public int compare(ICard card1, ICard card2) {
        return (card1.getValue() - card2.getValue());
    }

    @Override
    public int compareTo(ICard card) {
        return compare(this, card);
    }

    @Override
    public Suit getSuit() {
        return this.suit;
    }

    @Override
    public int getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        String strValue = "";
        if (this.value <= 10) {
            strValue = String.valueOf(this.value);
        } else {
            switch(this.value) {
                case 11: strValue = "Jack"; break;
                case 12: strValue = "Queen"; break;
                case 13: strValue = "King"; break;
                case 14: strValue = "Ace"; break;
            }
        }
        return strValue + " of " + this.suit.toString();
    }
}
