package lib;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import enums.Scores;
import enums.Stage;

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
    private int secondPotValue;
    private IPlayer secondPotOwner;
    
    public Dealer() {
    	human = new HPlayer();
        computer = new CPlayer();
        table_cards = new TableCards();
        deck = new Deck();
        bet = new Bet();
        pot = new Pot();
        this.secondPotOwner = null;
        this.secondPotValue = 0;
    }
    
    public void clear() {
        this.table_cards.clear();
        this.pot.clear();
        human.clear();
        computer.clear();
        bet.clear();
        this.secondPotOwner = null;
        this.secondPotValue = 0;
    }

    /**
     * Runs a token round between the two players and returns the winner
     * @param p1
     * @param p2
     * @return
     */
    public IPlayer playRound(IPlayer p1, IPlayer p2) {
        this.dealCards(p1, p2, deck);
        Stage stage = Stage.PRE_FLOP;
        while (stage != Stage.SHOW) {
            if (p1.getMoney() != 0 && p2.getMoney() != 0) {
                IPlayer non_folder = null;
                if (stage == Stage.PRE_FLOP) {
                    non_folder = this.conductBets(p1, p2);
                } else {
                    non_folder = this.conductBets(p2, p1);
                }
                if (non_folder != null) {
                    return non_folder;
                }
            }
            if (stage == Stage.PRE_FLOP) {
                table_cards.openFlop(deck);
                table_cards.updateStage();
                stage = Stage.PRE_TURN;
            } else if (stage == Stage.PRE_TURN) {
                table_cards.openTurn(deck);
                table_cards.updateStage();
                stage = Stage.PRE_RIVER;
            } else if (stage == Stage.PRE_RIVER) {
                table_cards.openRiver(deck);
                table_cards.updateStage();
                stage = Stage.PRE_SHOW;
            } else if (stage == Stage.PRE_SHOW) {
                stage = Stage.SHOW;
            }
        }
        this.printGameStatus();
        return this.decideWinner(p1, p2);
    }
    
    private IPlayer decideWinner(IPlayer p1, IPlayer p2) {
        p1.getHand().addAll(this.table_cards.getCards());
        p2.getHand().addAll(this.table_cards.getCards());
        Scores p1_score = new HandScore(p1.getHand()).getHandScore();
        Scores p2_score = new HandScore(p2.getHand()).getHandScore();
        System.out.println(p1.getName() + "'s Score: " + p1_score.toString());
        System.out.println(p2.getName() + "'s Score: " + p2_score.toString());
        if (p1_score.getValue() > p2_score.getValue()) {
            return p1;
        } else if (p1_score.getValue() < p2_score.getValue()) {
            return p2;
        }
        // random winner
        System.out.println("Random Winner");
        return p1;
    }

    /**
     * Conducts the round of bets among the players and returns the
     * other player if one of them folds and null otherwise
     * @param p1 +ve player
     * @param p2 -ve player
     * @return
     */
    private IPlayer conductBets(IPlayer p1, IPlayer p2) {
        bet.clear();
        int sign = 1;
        do {
            System.out.println("Bet: " + bet.getBet());
            // if the same player is asked to play again when the other one
            // runs out of money => break
            if (Math.signum(bet.getBet()) == Math.signum(sign)) {
                break;
            }
            IPlayer non_folder = this.handleGetBet(p1, p2, sign);
            if (non_folder != null) {
                return non_folder;
            }
            p1.placeBet(true);
            IPlayer tmp = p1;
            p1 = p2;
            p2 = tmp;
            sign *= -1;
        } while(bet.getBet() != 0 || !p1.hasPlacedBet() || !p2.hasPlacedBet());
        p1.placeBet(false);
        p2.placeBet(false);
        return null;
    }
    
    /**
     * Handles the getting of bets from the player p1 and returns
     * the other player if p1 folds else returns null.
     * @param p1
     * @param p2
     * @return
     */
    private IPlayer handleGetBet(IPlayer p1, IPlayer p2, int sign) {
        this.printGameStatus();
        int bet_value = p1.getBet(Math.abs(bet.getBet()));
        if (bet_value == Bet.FOLD) {
            return p2;
        } else if (bet_value == Bet.CALL) {
            this.handleCall(p1, sign);
        } else if (bet_value == Bet.ALLIN) {
            this.handleAllIn(p1, p2, sign);
        } else if (bet_value > 0) { // RAISE
            this.handleRaise(p1, bet_value, sign);
        }
        return null;
    }
    
    private void handleCall(IPlayer player, int sign) {
        int current_bet = Math.abs(bet.getBet());
        bet.updateBet(bet.getBet() + current_bet * sign);
        player.subMoney(current_bet);
        pot.addMoney(current_bet);
    }

    private void handleAllIn(IPlayer p1, IPlayer p2, int sign) {
        int current_bet = Math.abs(bet.getBet());
        int p1_money = p1.getMoney();
        bet.updateBet(bet.getBet() + p1_money * sign);
        pot.addMoney(p1_money);
        p1.subMoney(p1_money);
        this.secondPotValue =
                current_bet - p1_money > 0 ? current_bet - p1_money : 0;
        if (secondPotValue != 0) {
            pot.subMoney(secondPotValue);
            this.secondPotOwner = p2;
        }
    }

    private void handleRaise(IPlayer player, int bet_value, int sign) {
        int current_bet = Math.abs(bet.getBet());
        bet.updateBet(bet.getBet() + (current_bet + bet_value) * sign);
        player.subMoney(current_bet + bet_value);
        pot.addMoney(current_bet + bet_value);
    }

    private void printGameStatus() {
        System.out.println();
        System.out.println("********************************");
        System.out.println("**Game Status**");
        System.out.print("Table Cards => ");
        ArrayList<ICard> cards = this.table_cards.getCards();
        System.out.println(cards.toString());
        System.out.println("Pot Money => " + this.pot.getMoney());
        if (this.secondPotValue != 0) {
            System.out.println("2nd Pot Money => " + this.secondPotValue);
            System.out.println("2nd Pot Owner => " + secondPotOwner.getName());
        }
        System.out.println("Computer's Money => " + this.computer.getMoney());
        System.out.println("Human's Money => " + this.human.getMoney());
        System.out.println("Computer's Hand: " +
                this.computer.getHand().toString());
        System.out.println("Human's Hand: " +
                this.human.getHand().toString());
        System.out.println("********************************");
    }

    private void dealCards(IPlayer p1, IPlayer p2, IDeck deck) {
        deck.shuffle();
        p1.addCard(deck.dealCard());
        p2.addCard(deck.dealCard());
        p1.addCard(deck.dealCard());
        p2.addCard(deck.dealCard());
    }
    
    public boolean gameEnd(IPlayer p1, IPlayer p2){
    	if(p1.getMoney() == 0 || p2.getMoney() == 0)
    		return true;
    	else
    		return false;
    }
    
    public void blindBet(IPlayer p1, IPlayer p2, int smallBlind){
        this.printGameStatus();
        System.out.println("Adding the blinds");
        System.out.println("Small: " + p1.getName() + "\nBig: " + p2.getName());

    	if(p1.getMoney()<smallBlind && p2.getMoney()>=smallBlind*2){
            this.pot.addMoney(p1.getMoney() * 2);
    		p1.subMoney(p1.getMoney());
    		p2.subMoney(p1.getMoney());
    	}
    	else if(p1.getMoney()>=smallBlind && p2.getMoney()<smallBlind*2){
            this.pot.addMoney(smallBlind + p2.getMoney());
    		p1.subMoney(smallBlind);
    		p2.subMoney(p2.getMoney());
    	}
    	else if(p1.getMoney()<smallBlind && p2.getMoney()<smallBlind*2){
    		int subAmount = (p1.getMoney()<=p2.getMoney())?p1.getMoney():p2.getMoney();
    		this.pot.addMoney(subAmount * 2);
    		p1.subMoney(subAmount);
    		p2.subMoney(subAmount);
    	}
    	else{
    	    this.pot.addMoney(smallBlind * 3);
    		p1.subMoney(smallBlind);
    		p2.subMoney(smallBlind*2);
    	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
    	int smallBlind;
    	Dealer dealer = new Dealer();
        Random rand = new Random();
        IPlayer player = dealer.human;
        IPlayer computer = dealer.computer;
    	System.out.println("Please decide the small blind amount: ");
    	Scanner reader = new Scanner(System.in);
    	smallBlind = reader.nextInt();
    	System.out.println("Please enter your starting money (Computer will match you)");
    	int startingMoney = reader.nextInt();
    	player.addMoney(startingMoney);
    	computer.addMoney(startingMoney);
       
        int pickedNum = rand.nextInt(2);
        boolean dealerButton = true;
        if(pickedNum == 0)
        	dealerButton = true;
        else
        	dealerButton = false;
        
        int round = 0;
        while(!dealer.gameEnd(player, computer)){
            IPlayer winner = null;
        	if(dealerButton){
        		dealer.blindBet(player, computer, smallBlind);
        		winner = dealer.playRound(player, computer);
        	}
        	else{
        		dealer.blindBet(computer, player, smallBlind);
        		winner = dealer.playRound(computer, player);
        	}
            winner.addMoney(dealer.pot.getMoney());
            if (dealer.secondPotOwner != null) {
                dealer.secondPotOwner.addMoney(dealer.secondPotValue);
            }
            dealer.clear();

        	System.out.println("Round " + round + " winner: "
        	        + winner.getName());
        	dealerButton = !dealerButton;
        	round++;
        }
    }

}
