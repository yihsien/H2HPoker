package lib;

import java.util.ArrayList;

import interfaces.IBet;
import interfaces.ICard;
import interfaces.IDeck;
import interfaces.IPlayer;
import interfaces.IPot;
import interfaces.ITableCards;

public class Dealer {

    private IPlayer human;
    private IPlayer computer;
    private ITableCards table_cards;
    private IDeck deck;
    private IBet bet;
    private IPot pot;
    
    public Dealer() {
        human = null;       // = new HPlayer();
        computer = null;    // = new CPlayer();
        table_cards = new TableCards();
        deck = new Deck();
        bet = null;         // = new Bet();
        pot = new Pot();
    }

    public void playRound(IPlayer p1, IPlayer p2) {
        this.dealCards(p1, p2, deck);
        bet.clear();
        this.printGameStatus();
    }

    private void printGameStatus() {
        System.out.println("********************************");
        System.out.println("**Game Status**");
        System.out.print("Table Cards => ");
        ArrayList<ICard> cards = this.table_cards.getCards();
        System.out.println(cards.toString());
        System.out.println("Pot Money => " + this.pot.getMoney());
        System.out.println("Computer's Money => " + this.computer.getMoney());
        System.out.println("Human's Money => " + this.human.getMoney());
        System.out.println("********************************");
    }

    private void dealCards(IPlayer p1, IPlayer p2, IDeck deck) {
        deck.shuffle();
        p1.addCard(deck.dealCard());
        p2.addCard(deck.dealCard());
        p1.addCard(deck.dealCard());
        p2.addCard(deck.dealCard());
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        
    }

}
