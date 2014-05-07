package lib;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import players.HumanPlayer;
import players.RandomPlayer;

import enums.Scores;
import enums.Stage;

import interfaces.IBet;
import interfaces.ICard;
import interfaces.IDealer;
import interfaces.IDeck;
import interfaces.IMove;
import interfaces.IPlayer;
import interfaces.IPot;
import interfaces.ITableCards;

public class Dealer implements IDealer {
    private static Dealer dealerInstance;
	public int smallBlind;
    private IPlayer player1;
    private IPlayer player2;
    private ITableCards tableCards;
    private IDeck deck;
    private IBet bet;
    private IPot pot;
    private int secondPotValue;
    private IPlayer secondPotOwner;
    private static Stage stage = Stage.PRE_FLOP;
    private static int round = 0;
    private String sb;
    private int startingMoney;
    
    private Dealer() {
        if (dealerInstance != null) {
            return;
        }
    	player1 = null;
    	player2 = null;
        tableCards = new TableCards();
        deck = new Deck();
        bet = new Bet();
        pot = new Pot();
        this.secondPotOwner = null;
        this.secondPotValue = 0;
    }
    
    public void clear() {
        this.tableCards.clear();
        this.pot.clear();
        player1.clear();
        player2.clear();
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
        stage = Stage.PRE_FLOP;
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
                tableCards.openFlop(deck);
                tableCards.updateStage();
                //p1.setTotalBet(p1.getTempBet());
                p1.setTempBet(0);
                //p2.setTotalBet(p2.getTempBet());
                p2.setTempBet(0);
                stage = Stage.PRE_TURN;
            } else if (stage == Stage.PRE_TURN) {
                tableCards.openTurn(deck);
                tableCards.updateStage();
                //p1.setTotalBet(p1.getTotalBet()+p1.getTempBet());
                p1.setTempBet(0);
                //p2.setTotalBet(p2.getTotalBet()+p2.getTempBet());
                p2.setTempBet(0);
                stage = Stage.PRE_RIVER;
            } else if (stage == Stage.PRE_RIVER) {
                tableCards.openRiver(deck);
                tableCards.updateStage();
                //p1.setTotalBet(p1.getTotalBet()+p1.getTempBet());
                p1.setTempBet(0);
                //p2.setTotalBet(p2.getTotalBet()+p2.getTempBet());
                p2.setTempBet(0);
                stage = Stage.PRE_SHOW;
            } else if (stage == Stage.PRE_SHOW) {
                stage = Stage.SHOW;
            }
        }
        this.printGameStatus();
        return this.decideWinner(p1, p2);
    }
    
    /**
     * Decide the winner based upon the 2 cards in hand the 5 on the table
     * @param p1
     * @param p2
     * @return the winning player, null in case of tie
     */
    private IPlayer decideWinner(IPlayer p1, IPlayer p2) {
        p1.getHand().addAll(this.tableCards.getCards());
        p2.getHand().addAll(this.tableCards.getCards());
        Scores p1_score = new HandScore(p1.getHand()).getHandScore();
        Scores p2_score = new HandScore(p2.getHand()).getHandScore();
        System.out.println(p1.getName() + "'s Score: " + p1_score.toString());
        System.out.println(p2.getName() + "'s Score: " + p2_score.toString());
        if (p1_score.getValue() > p2_score.getValue()) {
            return p1;
        } else if (p1_score.getValue() < p2_score.getValue()) {
            return p2;
        }
        int tie_breaker = new HandScore(p1.getHand()).resolveTie(
                new HandScore(p2.getHand()), p1_score);
        if (tie_breaker == 1) {
            return p1;
        } else if (tie_breaker == -1) {
            return p2;
        }
        // null indicates tie
        return null;
    }

    /**
     * Conducts the round of bets among the players and returns the
     * other player if one of them folds and null otherwise
     * @param p1 +ve player
     * @param p2 -ve player
     * @return
     */
    private IPlayer conductBets(IPlayer p1, IPlayer p2) {
        // set up
        //bet.clear();
        int sign = 1;
        p1.placeBet(false);
        p2.placeBet(false);
        do {
            // if the same player is asked to play again when the other one
            // runs out of money => break
            if (Math.signum(bet.getBet()) == Math.signum(sign)) {
                break;
            } else if (p1.getMoney() == 0) {
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
            p1.getMove().setBetChoice(Bet.FOLD);
            p1.getMove().setBetChoice(0);
            return p2;
        } else if (bet_value == Bet.CALL) {
            p1.getMove().setBetChoice(Bet.CALL);
            p1.getMove().setBetChoice(Math.abs(bet.getBet()));
            this.handleCall(p1, sign);
        } else if (bet_value == Bet.ALLIN) {
            p1.getMove().setBetChoice(Bet.ALLIN);
            p1.getMove().setBetChoice(p1.getMoney());
            this.handleAllIn(p1, p2, sign);
        } else if (bet_value > 0) {                 // RAISE
            p1.getMove().setBetChoice(Bet.RAISE);
            p1.getMove().setBetChoice(bet_value);
            this.handleRaise(p1, bet_value, sign);
        } else {                                    // CHECK
            p1.getMove().setBetChoice(Bet.CHECK);
            p1.getMove().setBetChoice(0);
        }
        return null;
    }
    
    private void handleCall(IPlayer player, int sign) {
        int current_bet = Math.abs(bet.getBet());
        bet.updateBet(bet.getBet() + current_bet * sign);
        player.subMoney(current_bet);
        player.setTempBet(player.getTempBet()+current_bet);
        player.setTotalBet(player.getTotalBet()+current_bet);
        pot.addMoney(current_bet);
    }

    private void handleAllIn(IPlayer p1, IPlayer p2, int sign) {
        int current_bet = Math.abs(bet.getBet());
        int p1_money = p1.getMoney();
        bet.updateBet(bet.getBet() + p1_money * sign);
        pot.addMoney(p1_money);
        p1.subMoney(p1_money);
        p1.setTempBet(p1.getTempBet()+p1_money);
        p1.setTotalBet(p1.getTotalBet()+p1_money);
        this.secondPotValue =
                current_bet - p1_money > 0 ? current_bet - p1_money : 0;
        if (secondPotValue != 0) {
            pot.subMoney(secondPotValue);
            this.secondPotOwner = p2;
        }
    }

    private void handleRaise(IPlayer player, int bet_value, int sign) {
        //int current_bet = Math.abs(bet.getBet());
        bet.updateBet(bet.getBet() + (bet_value) * sign);
        player.subMoney(bet_value);
        pot.addMoney(bet_value);
    }

    private void printGameStatus() {
    	//System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println();
        System.out.println("********************************");
        switch(stage){
        case PRE_FLOP: System.out.println("**Round "+round+" (Pre-Flop)**"); break;
        case PRE_TURN: System.out.println("**Round "+round+" (Flop)**"); break;
        case PRE_RIVER: System.out.println("**Round "+round+" (Turn)**"); break;
        case PRE_SHOW: System.out.println("**Round "+round+" (River)**"); break;
        case SHOW: System.out.println("**Round "+round+" (Show)**"); break;
        case FINISH: System.out.println("**Round "+round+" (Final Result)**"); break;
        default: break;
        }
        System.out.print("Table Cards => ");
        ArrayList<ICard> cards = this.tableCards.getCards();
        System.out.println(cards.toString());
        System.out.println("Pot Money => " + this.pot.getMoney());
        if (this.secondPotValue != 0) {
            System.out.println("2nd Pot Money => " + this.secondPotValue);
            System.out.println("2nd Pot Owner => " + secondPotOwner.getName());
        }
        if(sb == this.player2.getName()){
        	System.out.println(this.player2.getName()+"(SB) => Money in Hand: "+this.player2.getMoney()+
        			", Money in pot: "+this.player2.getTotalBet()+", Total Money: "+
        			(this.player2.getMoney()+this.player2.getTotalBet()));
        	System.out.println(this.player1.getName()+"(BB) => Money in Hand: "+this.player1.getMoney()+
        			", Money in pot: "+this.player1.getTotalBet()+", Total Money: "+
        			(this.player1.getMoney()+this.player1.getTotalBet()));
        }
        else{
        	System.out.println(this.player2.getName()+"(BB) => Money in Hand: "+this.player2.getMoney()+
        			", Money in pot: "+this.player2.getTotalBet()+", Total Money: "+
        			(this.player2.getMoney()+this.player2.getTotalBet()));
        	System.out.println(this.player1.getName()+"(SB) => Money in hand: "+this.player1.getMoney()+
        			", Money in pot: "+this.player1.getTotalBet()+", Total Money: "+
        			(this.player1.getMoney()+this.player1.getTotalBet()));        	
        }
        	
        System.out.println(this.player2.getName()+"'s Hand: " +
                this.player2.getHand().toString());
        System.out.println(this.player1.getName()+"'s Hand: " +
                this.player1.getHand().toString());
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
        sb = p1.getName();
    	if(p1.getMoney()<smallBlind && p2.getMoney()>=smallBlind*2){
            this.pot.addMoney(p1.getMoney() * 2);
    		p1.subMoney(p1.getMoney());
    		p1.setTempBet(p1.getMoney());
    		p1.setTotalBet(p1.getMoney());
    		p2.subMoney(p1.getMoney());
    		p2.setTempBet(p2.getMoney());
    		p1.setTotalBet(p2.getMoney());
    	}
    	else if(p1.getMoney()>=smallBlind && p2.getMoney()<smallBlind*2){
            this.pot.addMoney(smallBlind + p2.getMoney());
    		p1.subMoney(smallBlind);
    		p1.setTempBet(smallBlind);
    		p1.setTotalBet(smallBlind);
    		bet.updateBet(smallBlind);
    		p2.subMoney(p2.getMoney());
    		p2.setTempBet(p2.getMoney());
    		p2.setTotalBet(p2.getMoney());
    		bet.updateBet(smallBlind-(p2.getMoney()));
    	}
    	else if(p1.getMoney()<smallBlind && p2.getMoney()<smallBlind*2){
    		int subAmount = (p1.getMoney()<=p2.getMoney())?p1.getMoney():p2.getMoney();
    		this.pot.addMoney(subAmount * 2);
    		p1.subMoney(subAmount);
    		p1.setTempBet(subAmount);
    		p1.setTotalBet(subAmount);
    		p2.subMoney(subAmount);
    		p2.setTempBet(subAmount);
    		p2.setTotalBet(subAmount);
    	}
    	else{
    	    this.pot.addMoney(smallBlind * 3);
    		p1.subMoney(smallBlind);
    		p1.setTempBet(smallBlind);
    		p1.setTotalBet(smallBlind);
    		bet.updateBet(smallBlind);
    		p2.subMoney(smallBlind*2);
    		p2.setTempBet(smallBlind*2);
    		p2.setTotalBet(smallBlind*2);
    		bet.updateBet(smallBlind-(smallBlind*2));
    	}
    }
    
    public void play() {
        Random rand = new Random();
        player1.addMoney(startingMoney);
        player2.addMoney(startingMoney);
       
        int pickedNum = rand.nextInt(2);
        boolean dealerButton = true;
        if(pickedNum == 0)
            dealerButton = true;
        else
            dealerButton = false;
        
        while(!this.gameEnd(player1, player2)){
            IPlayer winner = null;
            if(dealerButton){
                this.blindBet(player1, player2, smallBlind);
                winner = this.playRound(player1, player2);
            }
            else{
                this.blindBet(player2, player1, smallBlind);
                winner = this.playRound(player2, player1);
            }
            // handle tie
            if (winner == null) {
                int half = this.pot.getMoney()/2;
                player1.addMoney(half);
                player2.addMoney(this.pot.getMoney() - half);
            } else {
                winner.addMoney(this.pot.getMoney());
            }
            if (this.secondPotOwner != null) {
                this.secondPotOwner.addMoney(this.secondPotValue);
            }

            System.out.println("Round " + round + " winner: "
                    + winner.getName());
            player1.setTotalBet(0);
            player2.setTotalBet(0);
            stage = Stage.FINISH;
            this.clear();
            this.printGameStatus();
            
            // TO DO: auto start the next round. remove the next 3 lines
            System.out.println("Press enter to continue");
            Scanner gameControl = new Scanner(System.in);
            gameControl.nextLine();
            
            dealerButton = !dealerButton;
            round++;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Please decide the small blind amount: ");
        Scanner reader = new Scanner(System.in);
        int small_blind = reader.nextInt();
        System.out.println("Please enter your starting money (Computer will match you)");
        int starting_money = reader.nextInt();
        
        IPlayer player1 = new HumanPlayer("player");
        //player1 = new RandomPlayer("player");
        IPlayer player2 = new RandomPlayer("computer");
        //player2 = new HPlayer("human");
        
        Dealer dealer = Dealer.getDealerInstance();
        dealer.smallBlind = small_blind;
        dealer.startingMoney = starting_money;
        dealer.setPlayer1(player1);
        dealer.setPlayer2(player2);
        dealer.play();
    }
    
    public static Dealer getDealerInstance() {
        if (Dealer.dealerInstance == null) {
            Dealer.dealerInstance = new Dealer();
        }
        return Dealer.dealerInstance;
    }

    @Override
    public IPot getPot() {
        return this.pot;
    }

    @Override
    public ITableCards getTableCards() {
        return this.tableCards;
    }

    @Override
    public IBet getBet() {
        return this.bet;
    }

    @Override
    public IMove getOtherPlayerMove(IPlayer player) {
        if (player.getName().equals(this.player1.getName())) {
            return this.player2.getMove();
        } else {
            return this.player1.getMove();
        }
    }

    @Override
    public void setPlayer1(IPlayer player) {
        this.player1 = player;
    }

    @Override
    public void setPlayer2(IPlayer player) {
        this.player2 = player;
    }

}
