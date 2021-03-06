package enums;

public enum Stage {
    PRE_FLOP,   // stage before the flop is opened
    PRE_TURN,   // stage after the flop is opened & before the turn is opened
    PRE_RIVER,  // stage after the turn is opened & before the river is opened
    PRE_SHOW,   // stage after the river is opened & before the show of cards
    SHOW,       // show the cards stage
    FINISH      //stage when a round is finished
}
