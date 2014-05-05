package test;

import java.util.ArrayList;

import lib.HandScore;
import lib.Card;

import interfaces.ICard;
import enums.Scores;
import enums.Suit;

public class TestHandScore {

    private Scores getScore(int values[], Suit[] suits) {
        ArrayList<ICard> cards = new ArrayList<ICard>();
        for(int i = 0; i < 7; i++) {
            cards.add(new Card(values[i], suits[i]));
        }
        HandScore hs = new HandScore(cards);
        return hs.getHandScore();
    }
    
    public void testPair() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 13, 14, 5, 6, 7, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.HIGH_CARD) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }
        
        values =  new int[]{2, 2, 14, 5, 6, 7, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.PAIR) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{11, 2, 4, 5, 2, 7, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.PAIR) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{11, 2, 4, 5, 6, 8, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.PAIR) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestPair PASSED");
    }

    public void testTwoPairs() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 2, 4, 5, 7, 7, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.TWO_PAIRS) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }
        
        values =  new int[]{9, 2, 5, 5, 7, 7, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.TWO_PAIRS) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{9, 2, 8, 5, 7, 9, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.TWO_PAIRS) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{2, 2, 5, 5, 7, 7, 8};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.TWO_PAIRS) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestTwoPair PASSED");
    }

    public void testThreeOfAKind() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 6, 4, 5, 6, 7, 6};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.THREE_KIND) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{2, 12, 13, 6, 6, 6, 7};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.THREE_KIND) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestThreeOfAKind PASSED");
    }

    public void testStraight() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 3, 4, 5, 6, 7, 6};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.STRAIGHT) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{14, 2, 3, 4, 5, 11, 9};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.STRAIGHT) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{14, 12, 13, 10, 5, 11, 7};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.STRAIGHT) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestStraight PASSED");
    }

    public void testFlush() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 13, 4, 5, 6, 7, 6};
        suits = new Suit[]{Suit.SPADES, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.FLUSH) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{2, 13, 4, 5, 6, 7, 6};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.FLUSH) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestFlush PASSED");
    }
    
    public void testFullHouse() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 2, 4, 2, 6, 7, 6};
        suits = new Suit[]{Suit.SPADES, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.FULL_HOUSE) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{2, 2, 6, 2, 6, 7, 6};
        suits = new Suit[]{Suit.SPADES, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.FULL_HOUSE) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestFullHouse PASSED");
    }
    
    public void testFourOfAKind() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 2, 2, 2, 6, 7, 6};
        suits = new Suit[]{Suit.SPADES, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.FOUR_KIND) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{2, 2, 2, 2, 6, 6, 6};
        suits = new Suit[]{Suit.SPADES, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.FOUR_KIND) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestFourOfAKind PASSED");
    }
    
    public void testStraightFlush() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{2, 3, 4, 5, 6, 11, 9};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.STRAIGHT_FLUSH) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        values =  new int[]{2, 3, 4, 5, 8, 14, 9};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.STRAIGHT_FLUSH) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestStraightFlush PASSED");
    }
    
    public void testRoyalFlush() throws Exception {
        int values[];
        Suit[] suits;
        
        values =  new int[]{10, 11, 12, 13, 14, 11, 9};
        suits = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        if (this.getScore(values, suits) != Scores.ROYAL_FLUSH) {
            throw new Exception("Failed: " + this.getScore(values, suits));
        }

        System.out.println("TestRoyalFlush PASSED");
    }

    public static void main(String args[]) throws Exception {
        TestHandScore ths = new TestHandScore();
        ths.testPair();
        ths.testTwoPairs();
        ths.testThreeOfAKind();
        ths.testStraight();
        ths.testFlush();
        ths.testFullHouse();
        ths.testFourOfAKind();
        ths.testStraightFlush();
        ths.testRoyalFlush();
    }
}
