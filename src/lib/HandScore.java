package lib;

import interfaces.ICard;

import java.util.ArrayList;
import java.util.Arrays;

import enums.Scores;
import enums.Suit;

public class HandScore {

    private ArrayList<ICard> hand;
    private Card[] cards;
    private Scores hand_score = Scores.HIGH_CARD;
    
    public HandScore(ArrayList<ICard> hand) {
        this.hand = hand;
        this.cards = this.hand.toArray(new Card[this.hand.size()]);
        Arrays.sort(cards);
        this.hand_score = this.computeHandScore();
    }
    
    private Scores computeHandScore() {
        if (this.hasRoyalFlush()) {
            return Scores.ROYAL_FLUSH;
        } else if (this.hasStraightFlush()) {
            return Scores.STRAIGHT_FLUSH;
        } else if (this.hasFourOfAKind()) {
            return Scores.FOUR_KIND;
        } else if (this.hasFullHouse()) {
            return Scores.FULL_HOUSE;
        } else if (this.hasFlush()) {
            return Scores.FLUSH;
        } else if (this.hasStraight()) {
            return Scores.STRAIGHT;
        } else if (this.hasThreeOfAKind()) {
            return Scores.THREE_KIND;
        } else if (this.hasTwoPairs()) {
            return Scores.TWO_PAIRS;
        } else if (this.hasPair()) {
            return Scores.PAIR;
        } else {
            return Scores.HIGH_CARD;
        }
    }

    public Scores getHandScore() {
        return this.hand_score;
    }
    
    private boolean hasPair() {
        int count = 1;
        for (int i = 1; i < cards.length && count != 2; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            } else {
                count = 1;
            }
        }
        return count == 2;
    }
    
    private boolean hasTwoPairs() {
        int count = 1;
        int i;
        for (i = 1; i < cards.length && count != 2; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            } else {
                count = 1;
            }
        }
        if (count != 2) {
            return false;
        }
        count = 1;
        for (i = i + 1; i < cards.length && count != 2; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            } else {
                count = 1;
            }
        }
        return count == 2;
    }
    
    private boolean hasThreeOfAKind() {
        int count = 1;
        for (int i = 1; i < cards.length && count != 3; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            } else {
                count = 1;
            }
        }
        return count == 3;
    }

    private boolean hasStraight() {
        for (int i = 0; i < cards.length-1; i++) {
            int diff = cards[i+1].getValue() - cards[i].getValue();
            if (diff != -1) {
                return false;
            }
        }
        return true;
    }
    
    private boolean hasFlush() {
        Suit suit = this.hand.get(0).getSuit();
        for (ICard card : this.hand) {
            if (card.getSuit() != suit) {
                return false;
            }
        }
        return true;
    }
    
    private boolean hasFullHouse() {
        int count = 1;
        int count1 = 1;
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            } else {
                count1 = count;
                count = 1;
            }
        }
        int count2 = count;
        if ((count1 == 2 && count2 == 3) || (count1 == 3 && count2 == 2)) {
            return true;
        }
        return false;
    }
    
    private boolean hasFourOfAKind() {
        int count = 1;
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            }
        }
        return count == 4;
    }

    private boolean hasStraightFlush() {
        return this.hasFlush() && this.hasStraight();
    }

    private boolean hasRoyalFlush() {
        int req[] = {10, 11, 12, 13, 14};
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getValue() != req[i]) {
                return false;
            }
        }
        return this.hasFlush();
    }
}
