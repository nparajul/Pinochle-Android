/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */

package edu.ramapo.nparajul.pinochle.model.setup;

import java.util.Vector;

import edu.ramapo.nparajul.pinochle.model.players.Player;

/**
 ************************************************************
 * Round.java
 * Round class to play a round of the game.
 *
 * Member Variables:
 *       mFace - Face of the card object
 *       mSuit - Suit of the card object
 *       mCardNum - Unique Identifier of a card
 *       activeMeldUsedFor - Number of active melds the card is used for
 * Created by Nitesh Parajuli on 12/08/20.
 * Copyright © 2020 Nitesh Parajuli. All rights reserved.
 ************************************************************
 */

public class Round {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************
    private Deck mStockPile;
    private Card trumpCard;
    private Player mHuman;
    private Player mComputer;

    /**
     * Round::Round.
     * Round class parameterized constructor.
     * Constructs a round object by initializing member variables.
     * @param players Vector<Player> List of players of the game.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Round(Vector<Player> players){
          mHuman = players.get(0);
          mComputer = players.get(1);
    }

    /**
     * Round::beginRound.
     * Method to setup and begin the round.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void beginRound(){
        mStockPile = new Deck();
        mStockPile.createNewDeck();
        for(int i=0;i<3;i++){
            dealCards(mHuman,4);
            dealCards(mComputer,4);
        }
        trumpCard = mStockPile.drawCard();
        mHuman.setTrumpCard(trumpCard);
        mComputer.setTrumpCard(trumpCard);

    }

    /**
     * Round::loadRound.
     * Method to load a new round.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void loadRound(){
        mStockPile = new Deck();
    }

    /**
     * Round::getTrump.
     * Accessor method get the trump card object of the round.
     * @return Card Trump Card object of the round.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Card getTrump(){
        return trumpCard;
    }

    /**
     * Round::setTrumpCard.
     * Mutator method to set the trump card of the round.
     * @param trumpCard Card Object of the trump card.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    /**
     * Round::getTrumpCard.
     * Method get the trump card of the round to display in GUI layout.
     * @return Vector<String> A vector containing the trump card.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> getTrumpCard(){
        Vector<String> trumpString = new Vector<>();
        trumpString.add(trumpCard.getSuit().toLowerCase()+trumpCard.getFace().toLowerCase()+trumpCard.getCardNum());
        return trumpString;
    }

    /**
     * Round::dealCards.
     * Method to deal a number of cards to a player.
     * @param player Player The player to whom the card is dealt.
     * @param cards int The number of cards to be dealt.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void dealCards(Player player, int cards){
        for(int i=0;i<cards;i++){
            player.dealSingleCard(mStockPile.drawCard());
        }
    }

    /**
     * Round::getHandCards.
     * Method to get the hand cards of a player.
     * @param playerNum int the index of the player.
     * @return Vector<String> the vector containing the string representation of the hand cards.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> getHandCards(int playerNum){
        Vector<Card> playerHandCards = new Vector<>();

        if(playerNum==0){
            playerHandCards = mHuman.getHandPile();
        }
        else{
            playerHandCards = mComputer.getHandPile();
        }

        Vector<String> returnVec = new Vector<>();
        for(int i=0;i<playerHandCards.size();i++){
            returnVec.add(playerHandCards.get(i).getSuit().toLowerCase() + playerHandCards.get(i).getFace().toLowerCase() + playerHandCards.get(i).getCardNum());
        }
        return returnVec;
    }

    /**
     * Round::getCaptureCards.
     * Method to get the capture pile of a player.
     * @param playerNum int the index of the player.
     * @return Vector<String> the vector containing the string representation of the capture pile cards.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> getCaptureCards(int playerNum){
        Vector<Card> playerCaptureCards = new Vector<>();

        if(playerNum==0){
            playerCaptureCards = mHuman.getCapturePile();
        }
        else{
            playerCaptureCards = mComputer.getCapturePile();
        }

        Vector<String> returnVec = new Vector<>();
        for(int i=0;i<playerCaptureCards.size();i++){
            returnVec.add(playerCaptureCards.get(i).getSuit().toLowerCase() + playerCaptureCards.get(i).getFace().toLowerCase() + playerCaptureCards.get(i).getCardNum());
        }
        return returnVec;
    }

    /**
     * Round::getPlayableCards.
     * Method to get the all available cards of a player.
     * @param playerNum int the index of the player.
     * @return Vector<String> the vector containing the string representation of the all available playable cards.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> getPlayableCards(int playerNum){
        Vector<Card> playerHandCards = new Vector<>();

        if(playerNum==0){
            playerHandCards = mHuman.getPlayablePile();
        }
        else{
            playerHandCards = mComputer.getPlayablePile();
        }

        Vector<String> returnVec = new Vector<>();
        for(int i=0;i<playerHandCards.size();i++){
            returnVec.add(playerHandCards.get(i).getSuit().toLowerCase() + playerHandCards.get(i).getFace().toLowerCase() + playerHandCards.get(i).getCardNum());
        }
        return returnVec;
    }

    /**
     * Round::getStockPile.
     * Method to get the stock pile (in string) of the round.
     * @return Vector<String> the vector containing the string representation of the all available playable cards.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> getStockPile(){
        return mStockPile.getDeck();
    }

    /**
     * Round::getStockPile.
     * Method to get the deck object.
     * @return Deck the stock pile.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Deck stockDeck(){
        return mStockPile;
    }

    /**
     * Round::getPlayer
     * Method to get a player object
     * @param index int index of the player
     * @return Player the indexed player
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    public Player getPlayer(int index) {
        if(index == 0) {
            return mHuman;
        }
        else {
            return mComputer;
        }
    }


    /**
     * Round::getTurnWinner
     * Method to get the winner of a turn
     * @param leadCard Card Card played by the lead player
     * @param chaseCard Card Card played by the chase player
     * @param leadIndex int index of the lead player
     * @param chaseIndex int index of the chase player
     * @param isHelp Boolean flag to represent if function is called in help mode or not
     * @return int index of the turn winner
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    public int getTurnWinner(Card leadCard, Card chaseCard, int leadIndex, int chaseIndex, boolean isHelp) {
        int leadCardValue = leadCard.convertFaceToPoints();
        int chaseCardValue = chaseCard.convertFaceToPoints();
        int winnerIndex;

        if (leadCard.getSuit().equals(chaseCard.getSuit())) {
            if (leadCardValue >= chaseCardValue) {
                winnerIndex = leadIndex;
            }
            else {
                winnerIndex = chaseIndex;
            }
        }

        else
        {
            if(chaseCard.getSuit().equals(trumpCard.getSuit())) {
                winnerIndex = chaseIndex;
            }
            else {
                winnerIndex = leadIndex;
            }
        }

        if(!isHelp) {
            getPlayer(winnerIndex).addToCapturePile(leadCard);
            getPlayer(winnerIndex).addToCapturePile(chaseCard);
            getPlayer(winnerIndex).updatePlayerScore(leadCardValue);
            getPlayer(winnerIndex).updatePlayerScore(chaseCardValue);
        }

        return winnerIndex;
    }


    /**
     * Round::setupNextTurn.
     * Method to set up a new turn in a round.
     * @param leadIndex int index of the lead player.
     * @param chaseIndex int index of the chase player.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public void setupNextTurn(int leadIndex, int chaseIndex){

        if(mStockPile.getDeck().size() > 1) {
            getPlayer(leadIndex).dealSingleCard(mStockPile.drawCard());
            getPlayer(chaseIndex).dealSingleCard(mStockPile.drawCard());
        }
        else if (mStockPile.getDeck().size() == 1){
            getPlayer(leadIndex).dealSingleCard(mStockPile.drawCard());
            getPlayer(chaseIndex).dealSingleCard(trumpCard);
            Card newCard = new Card("n",trumpCard.getSuit(),trumpCard.getCardNum());
            trumpCard = newCard;
        }

    }


    /**
     * Round::roundEnd.
     * Method to indicate the end of a round.
     * Constructs a card object by initializing member variables.
     * @return Boolean True if both players have played all of their cards, false if otherwise.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Boolean roundEnd(){
        return mHuman.getPlayablePile().size() == 0 && mComputer.getPlayablePile().size() == 0;
    }
}
