package interfaces;

import java.util.HashMap;

import lib.Card;
import lib.HandScore;
import enums.Scores;

public interface IHandScore {

    public Scores getHandScore();
    
    public int resolveTie(HandScore hand_score, Scores score);
}
