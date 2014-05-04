package lib;

import enums.Suit;
import interfaces.ICard;
import interfaces.IDeck;

public class Deck implements IDeck {

    private static final int DECK_SIZE = 52;
    /**
     * An array of 52 cards.
     */
    private ICard[] deck;
    
    /**
     * Keeps track of the number of cards that have been dealt from
     * the deck so far.
     */
    private int cardsUsed;

    /**
     * Constructs a poker deck of playing cards. Initially the cards
     * are in a sorted order.  The shuffle() method is called to
     * randomize the order.
     */
    public Deck() {
        deck = new ICard[DECK_SIZE];
        int cardCt = 0; // How many cards have been created so far.
        for (Suit suit : Suit.values()) {
            for (int value = 2; value < 15; value++) {
                deck[cardCt] = new Card(value, suit);
                cardCt++;
            }
        }
        this.shuffle();
        cardsUsed = 0;
    }

    /**
     * Shuffle the deck into a random order.
     */
    @Override
    public void shuffle() {
       for ( int i = DECK_SIZE-1; i > 0; i-- ) {
          int rand = (int)(Math.random()*(i+1));
          Card temp = (Card) deck[i];
          deck[i] = deck[rand];
          deck[rand] = temp;
       }
       cardsUsed = 0;
    }

    /**
     * Removes the next card from the deck and return it.  It is illegal
     * to call this method if there are no more cards in the deck.
     * @return the card which is removed from the deck.
     * @throws IllegalStateException if there are no cards left in the deck
     */
    @Override
    public ICard dealCard() {
       if (cardsUsed == DECK_SIZE)
          throw new IllegalStateException("No cards are left in the deck.");
       cardsUsed++;
       return deck[cardsUsed - 1];
       // Programming note:  Cards are not literally removed from the array
       // that represents the deck.  We just keep track of how many cards
       // have been used.
    }
}
