package test;

import interfaces.ICard;

import java.util.ArrayList;

import lib.Card;
import lib.HandScore;
import enums.Scores;
import enums.Suit;

public class TestResolveTies {

    private HandScore getHandScore(int values[], Suit[] suits) {
        ArrayList<ICard> cards = new ArrayList<ICard>();
        for(int i = 0; i < 7; i++) {
            cards.add(new Card(values[i], suits[i]));
        }
        HandScore hs = new HandScore(cards);
        return hs;
    }

    public void testHighCard() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 5, 6, 7, 8};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 14, 5, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.HIGH_CARD) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.HIGH_CARD));
        }

        values1 =  new int[]{2, 3, 8, 5, 6, 7, 14};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 14, 5, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.HIGH_CARD) != 0) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.HIGH_CARD));
        }

        values1 =  new int[]{3, 3, 5, 6, 7, 8, 14};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 5, 6, 7, 8, 14};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.HIGH_CARD) != 0) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.HIGH_CARD));
        }

        System.out.println("HighCard PASSED");
    }
    
    public void testPair() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 5, 6, 7, 7};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 4, 6, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.PAIR) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.PAIR));
        }

        values1 =  new int[]{2, 3, 4, 5, 6, 8, 8};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 4, 5, 7, 8, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.PAIR) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.PAIR));
        }
        System.out.println("Pair PASSED");
    }
    
    public void testTwoPairs() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 6, 6, 7, 7};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 4, 4, 6, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.TWO_PAIRS) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.TWO_PAIRS));
        }

        values1 =  new int[]{2, 4, 4, 6, 6, 7, 9};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 4, 4, 6, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.TWO_PAIRS) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.TWO_PAIRS));
        }

        values1 =  new int[]{2, 4, 4, 6, 6, 7, 9};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 4, 4, 6, 6, 7, 7};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.TWO_PAIRS) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.TWO_PAIRS));
        }
        System.out.println("TwoPairs PASSED");
    }

    public void testThreeOfAKind() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 5, 7, 7, 7};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 6, 6, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.THREE_KIND) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.THREE_KIND));
        }

        values1 =  new int[]{2, 2, 2, 5, 7, 7, 7};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 8, 6, 6, 6, 8, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.THREE_KIND) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.THREE_KIND));
        }
        System.out.println("ThreeOfAKind PASSED");
    }

    public void testStraight() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{5, 4, 6, 6, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.STRAIGHT) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.STRAIGHT));
        }

        values1 =  new int[]{2, 3, 4, 5, 11, 9, 14};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{5, 4, 6, 6, 6, 7, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.STRAIGHT) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.STRAIGHT));
        }

        values1 =  new int[]{2, 3, 4, 5, 6, 7, 8};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{5, 4, 2, 3, 6, 12, 13};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.STRAIGHT) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.STRAIGHT));
        }

        values1 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 4, 5, 6, 12, 13};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.STRAIGHT) != 0) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.STRAIGHT));
        }
        System.out.println("Straight PASSED");
    }
    
    public void testFlush() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FLUSH) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FLUSH));
        }

        values1 =  new int[]{12, 3, 4, 5, 6, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.CLUBS};
        values2 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.HEARTS, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FLUSH) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FLUSH));
        }

        values1 =  new int[]{12, 3, 4, 5, 6, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.CLUBS};
        values2 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FLUSH) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FLUSH));
        }
        System.out.println("Flush PASSED");
    }
    
    public void testFullHouse() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 2, 3, 3, 3, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 2, 2, 3, 3, 9, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FULL_HOUSE) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FULL_HOUSE));
        }

        values1 =  new int[]{2, 2, 2, 5, 5, 5, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        values2 =  new int[]{2, 2, 3, 5, 5, 5, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FULL_HOUSE) != 0) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FULL_HOUSE));
        }
        
        System.out.println("FullHouse PASSED");
    }
    
    public void testFourOfAKind() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 7, 7, 7, 7};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 6, 6, 6, 6, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FOUR_KIND) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FOUR_KIND));
        }

        values1 =  new int[]{2, 2, 2, 7, 7, 7, 7};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{8, 8, 6, 6, 6, 8, 8};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.HEARTS, Suit.SPADES, Suit.SPADES};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.FOUR_KIND) != -1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.FOUR_KIND));
        }
        System.out.println("FourOfAKind PASSED");
    }

    public void testStraightFlush() throws Exception {
        int values1[];
        Suit[] suits1;
        int values2[];
        Suit[] suits2;
        HandScore hs1;
        HandScore hs2;
        
        values1 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.SPADES};
        values2 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.STRAIGHT_FLUSH) != 0) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.STRAIGHT_FLUSH));
        }

        values1 =  new int[]{2, 3, 4, 5, 6, 7, 10};
        suits1 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.CLUBS, Suit.SPADES};
        values2 =  new int[]{2, 3, 4, 5, 6, 9, 10};
        suits2 = new Suit[]{Suit.CLUBS, Suit.CLUBS, Suit.CLUBS, Suit.CLUBS,
                    Suit.CLUBS, Suit.SPADES, Suit.CLUBS};
        hs1 = this.getHandScore(values1, suits1);
        hs2 = this.getHandScore(values2, suits2);
        if (hs1.resolveTie(hs2, Scores.STRAIGHT_FLUSH) != 1) {
            throw new Exception("Failed: " +
                    hs1.resolveTie(hs2, Scores.STRAIGHT_FLUSH));
        }
        
        System.out.println("StraightFlush PASSED");
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        TestResolveTies ts = new TestResolveTies();
        ts.testHighCard();
        ts.testPair();
        ts.testTwoPairs();
        ts.testThreeOfAKind();
        ts.testStraight();
        ts.testFlush();
        ts.testFourOfAKind();
        ts.testStraightFlush();
        ts.testFullHouse();
    }

}
