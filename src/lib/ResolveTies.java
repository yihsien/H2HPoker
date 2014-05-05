package lib;

import java.util.Arrays;

import interfaces.IPlayer;
import enums.Scores;

public class ResolveTies {

    private static Card[] cards1;
    private static Card[] cards2;
    
    public static IPlayer resolveTies(IPlayer p1, IPlayer p2, Scores score) {
        Card[] cards1 = p1.getHand().toArray(new Card[p1.getHand().size()]);
        Arrays.sort(cards1);
        Card[] cards2 = p1.getHand().toArray(new Card[p1.getHand().size()]);
        Arrays.sort(cards2);
        
        switch (score) {
        case PAIR: return resolvePair(p1, p2);
        }
        return null;
    }

    private static IPlayer resolvePair(IPlayer p1, IPlayer p2) {
        
        int count = 1;
        for (int i = 1; i < cards1.length && count != 2; i++) {
            if (cards1[i].compareTo(cards1[i-1]) == 0) {
                count++;
            } else {
                count = 1;
            }
        }

        return null;
    }
}
