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
    }
    
    private void setUp() {
        this.cards = this.hand.toArray(new Card[this.hand.size()]);
        Arrays.sort(cards);
        computeMaps();
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
        this.setUp();
        this.hand_score = this.computeHandScore();
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
    
    /**
     * Resolves the winner from the hand_score based on provided Scores
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    public int resolveTie(HandScore hand_score, Scores score) {
        this.setUp();
        hand_score.setUp();
        switch (score) {
        case HIGH_CARD: return this.resolveHighCard(hand_score);
        case PAIR: return this.resolvePair(hand_score);
        case TWO_PAIRS: return this.resolveTwoPairs(hand_score);
        case THREE_KIND: return this.resolveThreeOfAKind(hand_score);
        case STRAIGHT: return this.resolveStraight(hand_score);
        case FLUSH: return this.resolveFlush(hand_score);
        case FULL_HOUSE: return this.resolveFullHouse(hand_score);
        case FOUR_KIND: return this.resolveFourOfAKind(hand_score);
        case STRAIGHT_FLUSH: return this.resolveStraightFlush(hand_score);
        case ROYAL_FLUSH: return this.resolveRoyalFlush(hand_score);
        default:
            break;
        }
        return 1;
    }
    
    /**
     * Resolves the winner from the hand_score based on HIGH_CARD
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveHighCard(HandScore hand_score) {
        Card[] cards = hand_score.cards;
        for (int i = this.cards.length - 1, j = cards.length - 1;
                i >= 2 && j >= 2;
                i--, j--) {
            int value1 = this.cards[i].getValue();
            int value2 = cards[j].getValue();
            if (value1 != value2) {
                return (int)Math.signum(value1 - value2);
            }
        }
        return 0;
    }
    
    /**
     * Resolves the winner from the hand_score based on PAIR
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolvePair(HandScore hand_score) {
        Card[] cards = hand_score.cards;
        HashMap<Integer, Integer> value_map = hand_score.valueMap;
        for (int i = this.cards.length - 1, j = cards.length - 1;
                i >= 0 && j >= 0;) {
            int value1 = this.cards[i].getValue();
            int value2 = cards[j].getValue();
            if (valueMap.get(value1) != 2) {
                i--;
                continue;
            } else if (value_map.get(value2) != 2) {
                j--;
                continue;
            }
            if (value1 != value2) {
                return (int)Math.signum(value1 - value2);
            } else {
                i--;
                j--;
                continue;
            }
        }
        // tie in both the hands
        return this.resolveTie(hand_score, Scores.HIGH_CARD);
    }

    /**
     * Resolves the winner from the hand_score based on TWO_PAIRS
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveTwoPairs(HandScore hand_score) {
        return this.resolvePair(hand_score);
    }
    
    /**
     * Resolves the winner from the hand_score based on THREE_KIND
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveThreeOfAKind(HandScore hand_score) {
        Card[] cards = hand_score.cards;
        HashMap<Integer, Integer> value_map = hand_score.valueMap;
        for (int i = this.cards.length - 1, j = cards.length - 1;
                i >= 0 && j >= 0;) {
            int value1 = this.cards[i].getValue();
            int value2 = cards[j].getValue();
            if (valueMap.get(value1) != 3) {
                i--;
                continue;
            } else if (value_map.get(value2) != 3) {
                j--;
                continue;
            }
            if (value1 != value2) {
                return (int)Math.signum(value1 - value2);
            } else {
                i--;
                j--;
                continue;
            }
        }
        // tie in both the hands
        return this.resolveTie(hand_score, Scores.HIGH_CARD);
    }
    
    private Card getStraightHighCard(HandScore hand_score) {
        if (!hand_score.hasStraight()) {
            throw new IllegalArgumentException("Hand does not have a " +
                    "STRAIGHT. It has " + hand_score.getHandScore().toString());
        }
        Card[] cards = hand_score.cards;
        Card high_card = null;
        
        int prevIndex = cards.length - 1;
        int count = 1;
        for (int i = cards.length - 2; i >= 0 && count != 5; i--) {
            int diff = cards[prevIndex].getValue() - cards[i].getValue();
            // ignore duplicates
            if (diff == 0) {
                continue;
            }
            if (diff == 1) {
                if (high_card == null) {
                    high_card = cards[prevIndex];
                }
                count++;
            } else {
                // reset count so restart comparing
                count = 1;
                high_card = null;
            }
            prevIndex = i;
        }
        if (count == 5) {
            return high_card;
        }
        
        // Only possibility left - A 2 3 4 5
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getValue() == 5) {
                return cards[i];
            }
        }
        return null;
    }

    /**
     * Resolves the winner from the hand_score based on STRAIGHT
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveStraight(HandScore hand_score) {
        Card high_card1 = this.getStraightHighCard(this);
        Card high_card2 = this.getStraightHighCard(hand_score);
        
        if (high_card1.getValue() != high_card2.getValue()) {
            return (int)Math.signum(
                    high_card1.getValue() - high_card2.getValue());
        }
        return 0;
    }

    /**
     * Resolves the winner from the hand_score based on FLUSH
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveFlush(HandScore hand_score) {
        Suit flush_suit = null;
        for (Suit suit : this.suitMap.keySet()) {
            if (this.suitMap.get(suit) >= 5) {
                flush_suit = suit;
                break;
            }
        }
        @SuppressWarnings("unchecked")
        HandScore hs1 = new HandScore((ArrayList<ICard>) this.hand.clone());
        @SuppressWarnings("unchecked")
        HandScore hs2 = new HandScore(
                (ArrayList<ICard>) hand_score.hand.clone());
        for (ICard card : this.hand) {
            if (card.getSuit() != flush_suit) {
                hs1.hand.remove(card);
            }
        }
        for (ICard card : hand_score.hand) {
            if (card.getSuit() != flush_suit) {
                hs2.hand.remove(card);
            }
        }
        return hs1.resolveTie(hs2, Scores.HIGH_CARD);
    }
    
    /**
     * Resolves the winner from the hand_score based on FULL_HOUSE
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveFullHouse(HandScore hand_score) {
        HashMap<Integer, Integer> value_map = hand_score.valueMap;
        int value1_3 = -1;
        int value1_2 = -1;
        for (Integer key : this.valueMap.keySet()) {
            if (valueMap.get(key) == 3) {
                if (value1_3 == -1) {
                    value1_3 = key;
                } else {
                    value1_2 = Math.max(value1_2, Math.min(value1_3, key));
                    value1_3 = Math.max(value1_3, key);
                }
            } else if (valueMap.get(key) == 2) {
                value1_2 = Math.max(value1_2, key);;
            }
        }
        
        int value2_3 = -1;
        int value2_2 = -1;
        for (Integer key : value_map.keySet()) {
            if (value_map.get(key) == 3) {
                if (value2_3 == -1) {
                    value2_3 = key;
                } else {
                    value2_2 = Math.max(value2_2, Math.min(value2_3, key));
                    value2_3 = Math.max(value2_3, key);
                }
            } else if (value_map.get(key) == 2) {
                value2_2 = Math.max(value2_2, key);;
            }
        }
        
        if (value1_3 != value2_3) {
            return (int)Math.signum(value1_3 - value2_3);
        } else if (value1_2 != value2_2) {
            return (int)Math.signum(value1_2 - value2_2);
        }
        return 0;
    }

    /**
     * Resolves the winner from the hand_score based on FOUR_KIND
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveFourOfAKind(HandScore hand_score) {
        Card[] cards = hand_score.cards;
        HashMap<Integer, Integer> value_map = hand_score.valueMap;
        int value1 = 0;
        int value2 = 0;
        for (int i = this.cards.length - 1, j = cards.length - 1;
                i >= 0 && j >= 0;) {
            value1 = this.cards[i].getValue();
            value2 = cards[j].getValue();
            if (valueMap.get(value1) != 4) {
                i--;
                continue;
            } else if (value_map.get(value2) != 4) {
                j--;
                continue;
            }
            if (value1 != value2) {
                return (int)Math.signum(value1 - value2);
            }
            break;
        }
        throw new IllegalArgumentException("Cannot be a tie in 4 "+ 
                "of a kind: value1 = " + value1 + ", value2 = " + value2);
    }

    /**
     * Resolves the winner from the hand_score based on STRAIGHT_FLUSH
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveStraightFlush(HandScore hand_score) {
        return this.resolveTie(hand_score, Scores.STRAIGHT);
    }
    
    /**
     * Resolves the winner from the hand_score based on ROYAL_FLUSH
     * @param hand_score
     * @return 1 if this is the winner, -1 if other is the winner, 0 - tie
     */
    private int resolveRoyalFlush(HandScore hand_score) {
        // only possibility is a tie
        return 0;
    }
}
