/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */

package edu.ramapo.nparajul.pinochle.model.setup;

import java.util.Random;
import java.util.Vector;

import edu.ramapo.nparajul.pinochle.model.players.Computer;
import edu.ramapo.nparajul.pinochle.model.players.Human;
import edu.ramapo.nparajul.pinochle.model.players.Player;

/**
 ************************************************************
 * Game.java
 * Main Game class to play the game.
 *
 * Member Variables:
 *       mHuman - Human player object
 *       mComputer - Computer player object
 *       gameRounds - the round number
 *       mRound - The current round object
 * Created by Nitesh Parajuli on 12/08/20.
 * Copyright © 2020 Nitesh Parajuli. All rights reserved.
 ************************************************************
 */
public class Game {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************

    private Vector<Player> players = new Vector<>();
    Human mHuman = new Human();
    Computer mComputer = new Computer();
    private int gameRounds;
    Round mRound;

    /**
     * Game::Game.
     * Game class default constructor.
     * Constructs a game object by initializing its variables.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Game() {
        gameRounds = 0;
        players.add(mHuman);
        players.add(mComputer);
    }

    /**
     * Game::play.
     * Method to play a new game.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void play(){

        gameRounds++;
        mRound = new Round(players);
        mRound.beginRound();

    }

    /**
     * Game::load.
     * Method to load a saved game.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void load(){
        gameRounds = 0;
        mRound = new Round(players);
        mRound.loadRound();
    }

    /**
     * Game::getRound.
     * Accessor method to get the round object.
     * @return Round the current round object
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Round getRound(){
        return mRound;
    }

    /**
     * Game::getRoundNum.
     * Accessor method to get the round number.
     * @return int the current round number.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public int getRoundNum(){
        return gameRounds;
    }

    /**
     * Game::setRoundNum.
     * Mutator method to set the round number.
     * @param roundNum int the round number to be set.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void setRoundNum(int roundNum){
        gameRounds = roundNum;
    }

    /**
     * Game::tossWinner.
     * Method to set the round number.
     * @param userChoice String toss choice made by the user.
     * @return String winner of the toss
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public String tossWinner(String userChoice) {
        Random random = new Random();
        int rand = random.nextInt(2);
        String toss_val = "";
        String winner = "";

        if (rand == 0){
            toss_val = "Heads";
        }
        else {
            toss_val = "Tails";
        }

        if (userChoice.equals(toss_val)){
            winner = "Human";
        }
        else {
            winner = "Computer";
        }

        return winner;
    }

    /**
     * Game::setUpNewRound.
     * Method to set up a new round after the end of a round.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void setUpNewRound(){
        int newRound = getRoundNum() + 1;
        getRound().getPlayer(0).clearPiles();
        getRound().getPlayer(1).clearPiles();
        mRound = new Round(players);
        setRoundNum(newRound);
        mRound.beginRound();
    }

}
