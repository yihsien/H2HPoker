package lib;

import interfaces.ICard;
import interfaces.IHandScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import enums.Scores;
import enums.Suit;

public class HandScore implements IHandScore {

    private ArrayList<ICard> hand;
    private Card[] cards;
    private Scores hand_score = Scores.HIGH_CARD;
    private HashMap<Suit, Integer> suitMap;
    private HashMap<Integer, Integer> valueMap;
    
    public HandScore(ArrayList<ICard> hand) {
        this.hand = hand;
        this.cards = this.hand.toArray(new Card[this.hand.size()]);
        Arrays.sort(cards);
        computeMaps();
        this.hand_score = this.computeHandScore();
    }
    
    private void computeMaps() {
        this.suitMap = new HashMap<Suit, Integer>();
        for (ICard card : this.hand) {
            Suit suit = card.getSuit();
            if (this.suitMap.containsKey(suit)) {
                this.suitMap.put(suit, this.suitMap.get(suit) + 1);
            } else {
                this.suitMap.put(suit,  1);
            }
        }

        this.valueMap = new HashMap<Integer, Integer>();
        for (ICard card : this.hand) {
            int value = card.getValue();
            if (this.valueMap.containsKey(value)) {
                this.valueMap.put(value, this.valueMap.get(value) + 1);
            } else {
                this.valueMap.put(value,  1);
            }
        }
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
        for (Integer key : this.valueMap.keySet()) {
            if (valueMap.get(key) >= 2) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasTwoPairs() {
        int count = 0;
        for (Integer key : this.valueMap.keySet()) {
            if (valueMap.get(key) >= 2) {
                count++;
            }
        }
        return count >= 2;
    }
    
    private boolean hasThreeOfAKind() {
        for (Integer key : this.valueMap.keySet()) {
            if (valueMap.get(key) >= 3) {
                return true;
            }
        }
        return false;
    }

    private boolean hasStraight() {
        int prevIndex = 0;
        int count = 1;
        for (int i = 1; i < cards.length && count != 5; i++) {
            int diff = cards[i].getValue() - cards[prevIndex].getValue();
            // ignore duplicates
            if (diff == 0) {
                continue;
            }
            if (diff == 1) {
                count++;
            } else {
                // reset count so restart comparing
                count = 1;
            }
            prevIndex = i;
        }
        if (count == 5) {
            return true;
        }
        
        // A 2 3 4 5 - check
        int[] values = {14, 2, 3, 4, 5};
        
        boolean isPresent;
        for (int i = 0; i < values.length; i++) {
            isPresent = false;
            for (int j = 0; j < cards.length; j++) {
                if (cards[j].getValue() == values[i]) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                return false;
            }
        }
        return true;
    }
    
    private boolean hasFlush() {
        for (Suit key : suitMap.keySet()) {
            if (suitMap.get(key) >= 5) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasFullHouse() {
        int count2 = 0;
        int count3 = 0;
        for (Integer key : this.valueMap.keySet()) {
            if (valueMap.get(key) == 3) {
                count3++;
            } else if (valueMap.get(key) == 2) {
                count2++;
            }
        }
        return count3 >= 1 && (count3 + count2) >= 2;
    }
    
    private boolean hasFourOfAKind() {
        int count = 1;
        for (int i = 1; i < cards.length && count != 4; i++) {
            if (cards[i].compareTo(cards[i-1]) == 0) {
                count++;
            } else {
                count = 1;
            }
        }
        return count == 4;
    }

    private boolean hasStraightFlush() {
        return this.hasFlush() && this.hasStraight();
    }

    private boolean hasRoyalFlush() {
        int req[] = {10, 11, 12, 13, 14};
        for (int key = 0; key < req.length; key++) {
            if (!this.valueMap.containsKey(req[key])) {
                return false;
            }
        }
        return this.hasFlush();
    }
}
