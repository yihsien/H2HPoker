package enums;

public enum Scores {
    HIGH_CARD(0),
    PAIR(1),
    TWO_PAIRS(2),
    THREE_KIND(3),
    STRAIGHT(4),
    FLUSH(5),
    FULL_HOUSE(6),
    FOUR_KIND(7),
    STRAIGHT_FLUSH(8),
    ROYAL_FLUSH(9);
    
    private int score = 0;
    
    private Scores(int value) {
        this.score = value;
    }
    
    public int getValue() {
        return this.score;
    }
}
