package enums;

public enum Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS;
    
    @Override
    public String toString() {
        switch(this) {
        case SPADES: return "Spades";
        case HEARTS: return "Hearts";
        case DIAMONDS: return "Diamonds";
        case CLUBS: return "Clubs";
        default:
            throw new IllegalArgumentException("Illegal playing card suit");
        }
    }
}
