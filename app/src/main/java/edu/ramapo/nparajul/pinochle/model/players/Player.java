/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */

package edu.ramapo.nparajul.pinochle.model.players;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import edu.ramapo.nparajul.pinochle.model.setup.Card;

/**
 ************************************************************
 * Player.java
 * Player class to create player objects. Implements behavior of a player object.
 * Created by Nitesh Parajuli on 12/08/20.
 * Copyright © 2020 Nitesh Parajuli. All rights reserved.
 ************************************************************
 */
abstract public class Player {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************
    protected Vector<Card> mHandPile;
    protected Vector<Card> mCapturePile;
    protected Vector<Card> mPlayablePile;
    protected Vector<Card> mMeldPile;
    protected HashMap<String, Vector<String>> meldMap;
    protected HashMap<String, Vector<Vector<String>>> activeMelds;
    protected int mScore;
    protected int mTotalScore;
    protected Card latestPlayed;
    protected Card mTrumpCard;

    /**
     * Player::Player.
     * player class default constructor.
     * Constructs a player object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Player(){
        mHandPile = new Vector<>();
        mCapturePile = new Vector<>();
        mPlayablePile = new Vector<>();
        mMeldPile = new Vector<>();
        meldMap = new HashMap<>();
        activeMelds = new HashMap<>();
        mScore = 0;
        mTotalScore = 0;
    }

    /**
     * Player::getHandPile.
     * Accessor method to get the hand pile of a player.
     * @return Vector<Card> Vector with all cards in hand pile.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public Vector<Card> getHandPile(){
        return this.mHandPile;
    }

    /**
     * Player::getCapturePile.
     * Accessor method to get the capture pile of a player.
     * @return Vector<Card> Vector with all cards in capture pile.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public Vector<Card> getCapturePile(){
        return this.mCapturePile;
    }

    /**
     * Player::getPlayablePile.
     * Accessor method to get the playable pile of a player.
     * @return Vector<Card> Vector with all playable cards.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public Vector<Card> getPlayablePile(){
        return this.mPlayablePile;
    }

    /**
     * Player::getTotalScore.
     * Accessor method to get the total score of a player.
     * @return int player's total score.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int getTotalScore() {
        return mTotalScore;
    }

    /**
     * Player::getScore.
     * Accessor method to get the round score of a player.
     * @return int player's round score.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int getScore(){
        return this.mScore;
    }

    /**
     * Player::getMeldMap.
     * Accessor method to get the player's meld card history.
     * @return HashMap<String,Vector<String>> map containing a player's meld cards and melds they've been used for.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public HashMap<String,Vector<String>> getMeldMap(){
        return this.meldMap;
    }

    /**
     * Player::getActiveMelds.
     * Accessor method to get the player's currently active melds.
     * @return HashMap<String,Vector<Vector<String>>> map containing a player's current melds and the associated cards.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public HashMap<String, Vector<Vector<String>>> getActiveMelds(){
        return this.activeMelds;
    }

    /**
     * Player::getLatestPlayed.
     * Accessor method to get the player's latest move.
     * @return Card the most recently played card
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public Card getLatestPlayed() {
        return latestPlayed;
    }

    /**
     * Player::setTotalScore.
     * Mutator method to set the player's total score.
     * @param roundScore int player's total score for a round.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setTotalScore(int roundScore) {
        mTotalScore += roundScore;
    }

    /**
     * Player::setTrumpCard.
     * Mutator method to set the trump card.
     * @param trumpCard Card trump card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setTrumpCard(Card trumpCard){
        this.mTrumpCard = trumpCard;
    }

    /**
     * Player::setActiveMelds.
     * Mutator method to set player's active melds.
     * @param meld String the type of meld to set.
     * @param cards Vector<String> list of cards (string representation) used in the meld
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setActiveMelds(String meld, Vector<String> cards){
        Vector<Vector<String>> currCards = activeMelds.get(meld);
        if(currCards == null){
            currCards = new Vector<>();
            currCards.add(cards);

        }
        else{
            currCards.add(cards);
        }

        activeMelds.put(meld,currCards);
    }

    /**
     * Player::setLatestPlayed.
     * Mutator method to set player's latest move.
     * @param card Card player's latest played carf.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setLatestPlayed(Card card) {
        latestPlayed = card;
    }

    abstract public Card makeMove(String leadCardStr, Card trumpCard);

    abstract public Vector<String> declareMeld ();

    /**
     * Player::dealSingleCard.
     * Helper method to deal a card to a player.
     * @param card Card the card to be dealt.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void dealSingleCard(Card card){
        mHandPile.add(card);
        mPlayablePile.add(card);
    }

    /**
     * Player::addToCapturePile.
     * Helper method to add a card to a player's capture pile.
     * @param card Card the card to be added in the capture pile.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void addToCapturePile(Card card){
        mCapturePile.add(card);
    }

    /**
     * Player::updatePlayerScore.
     * Helper method to update a player's round score.
     * @param score int the score that needs to be added to a player's round score.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void updatePlayerScore(int score){
        mScore += score;
    }

    /**
     * Player::updateMeldMap.
     * Helper method to update a player's meld map.
     * @param card the meld card to be added/updated.
     * @param meldType the meld type linked with the card to be added/updated.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void updateMeldMap(String card, String meldType){
        if(meldMap.containsKey(card.toUpperCase())){
            Vector<String> existingVec = meldMap.get(card.toUpperCase());
            existingVec.add(meldType);
            meldMap.put(card.toUpperCase(),existingVec);

        }
        else{
            Vector<String > newVec = new Vector<>();
            newVec.add(meldType);
            meldMap.put(card.toUpperCase(), newVec);
        }

        for(int i=0;i<mHandPile.size();i++){
            Card currentCard = mHandPile.get(i);
            if(     (currentCard.getFace().toUpperCase().charAt(0) == card.toUpperCase().charAt(0))&&
                    (currentCard.getSuit().toUpperCase().charAt(0) == card.toUpperCase().charAt(1))&&
                    (currentCard.getCardNum() == Character.getNumericValue(card.toUpperCase().charAt(2)) ))
            {
                mMeldPile.add(mHandPile.get(i));
                mHandPile.remove(i);
                break;
            }
        }

    }

    /**
     * Player::setActiveMeldsNum.
     * Helper method to update meld count of a player's card.
     * @param card Card the card object which needs meld count update.
     * @param num int the number of melds to be added.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setActiveMeldsNum(String card, int num){

        for(int i=0;i<mPlayablePile.size();i++){
            Card currentCard = mPlayablePile.get(i);
            if(     (currentCard.getFace().toUpperCase().charAt(0) == card.toUpperCase().charAt(0))&&
                    (currentCard.getSuit().toUpperCase().charAt(0) == card.toUpperCase().charAt(1))&&
                    (currentCard.getCardNum() == Character.getNumericValue(card.toUpperCase().charAt(2)) )) {
                mPlayablePile.get(i).setActiveMeldUsedFor(num);
            }
        }
    }

    /**
     * Player::getCard.
     * Helper method to get card object from a player's pile.
     * @param cardRec String card represented in string.
     * @return Card the card object of the cardRec.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public Card getCard(String cardRec){

        Card returnCard = new Card();
        String card = String.valueOf(cardRec.charAt(1))  +String.valueOf(cardRec.charAt(0))+String.valueOf(cardRec.charAt(2));
        for(int i=0;i<mPlayablePile.size();i++){
            Card currentCard = mPlayablePile.get(i);
            if(     (currentCard.getFace().toUpperCase().charAt(0) == card.toUpperCase().charAt(0))&&
                    (currentCard.getSuit().toUpperCase().charAt(0) == card.toUpperCase().charAt(1))&&
                    (currentCard.getCardNum() == Character.getNumericValue(card.toUpperCase().charAt(2)) )) {
                returnCard = currentCard;

            }
        }
        return returnCard;

    }

    /**
     * Player::removeCardFromHand.
     * Helper method to remove a card from the hand pile after it's played or used for meld.
     * @param card Card the card object which needs to be removed.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void removeCardFromHand(Card card){

        boolean found = false;
        int index = -1;

        for(Card eachCard:mHandPile)
        {
            index++;
            if((eachCard.getFace() + eachCard.getSuit() + eachCard.getCardNum()).equals(card.getFace() + card.getSuit() + card.getCardNum()))
            {
                found = true;
                break;
            }
        }

        if(index>=0 && found)
        {
            mHandPile.remove(index);
        }


    }

    /**
     * Player::removeCardFromPlayable.
     * Helper method to remove a card from the playable pile after it's played.
     * @param card Card the card object which needs to be removed.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void removeCardFromPlayable(Card card){

        int index = -1;

        for(Card eachCard:mPlayablePile) {
            index++;
            if((eachCard.getFace() + eachCard.getSuit() + eachCard.getCardNum()).equals(card.getFace() + card.getSuit() + card.getCardNum())){
                break;
            }
        }

        if(index>=0)
        {
            mPlayablePile.remove(index);
        }
    }


    /**
     * Player::removeCardFromPile.
     * Helper method to remove a card from all piles and map after it's played.
     * @param card Card the card object which needs to be removed.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void removeCardFromPile(Card card){

        removeCardFromHand(card);
        removeCardFromPlayable(card);

        String cardStr = card.getFace()+card.getSuit()+card.getCardNum();
        HashMap<String,Integer> toRemove = new HashMap<>();
        Vector<String> emptyMelds = new Vector<>();

        for(String meld: activeMelds.keySet()) {
            Boolean found = false;
            for(int count1 = 0; count1< activeMelds.get(meld).size();count1++){
                for(int count2 =0;count2<activeMelds.get(meld).get(count1).size(); count2++){

                    if(activeMelds.get(meld).get(count1).get(count2).equals(cardStr)){
                        found = true;
                        setActiveMeldsNum(cardStr,-1);
                        break;

                    }

                }

                if(found){
                    Vector<String> removeVec = activeMelds.get(meld).get(count1);
                    boolean rem_ele = removeVec.remove(cardStr);
                    if(rem_ele){
                        for(int i=0;i<removeVec.size();i++) {
                            String removeCard = removeVec.get(i);
                            removeCard=removeCard.toLowerCase();
                            removeCard = String.valueOf(removeCard.charAt(1)) +String.valueOf(removeCard.charAt(0))  +removeCard.charAt(2);
                            Card check = getCard(removeCard);
                            if (check.getActiveMeldUsedFor() == 1) {
                                mHandPile.add(check);
                            }
                            setActiveMeldsNum(check.getFace()+check.getSuit()+check.getCardNum(),-1);
                        }

                    }

                    toRemove.put(meld,count1);
                    found = false;
                    }
                }
            if(activeMelds.get(meld).size() == 0){
                emptyMelds.add(meld);
            }

        }

        for(String each: toRemove.keySet()){
            Vector<Vector<String>> removeVec= activeMelds.get(each);
            int index = toRemove.get(each);
            removeVec.remove(index);
            activeMelds.put(each,removeVec);

        }

        if(meldMap.containsKey(cardStr)){
            meldMap.remove(cardStr);
        }

    }

    /**
     * Player::getMeldType.
     * Helper method to get the meld type based on cards supplied.
     * @param meldCards Vector<String> the cards supplied for the meld.
     * @param trumpSuit char the suit of the trump card
     * @return String the meld type returned after evaluation
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public String getMeldType(Vector<String> meldCards, char trumpSuit) {
        int numCards = meldCards.size();
        String meldType = "";

        if(numCards == 1){
            if(meldCards.get(0).charAt(0) == '9' && meldCards.get(0).charAt(1) == trumpSuit){
                meldType = "Dix";
            }else{
                meldType = "None";
            }
        }

        else if (numCards == 2) {
            int i = 0;

            if ((meldCards.get(i).charAt(0) == 'J' && meldCards.get(i).charAt(1) == 'D') && (meldCards.get(i+1).charAt(0) == 'Q' && meldCards.get(i+1).charAt(1) == 'S')) {
                meldType = "Pinochle";
            }
            else if ((meldCards.get(i).charAt(0) == 'Q' && meldCards.get(i).charAt(1) == 'S') && (meldCards.get(i+1).charAt(0) == 'J' && meldCards.get(i+1).charAt(1) == 'D')) {
                meldType = "Pinochle";
            }
            else if ((meldCards.get(i).charAt(0) == 'K' && meldCards.get(i).charAt(1)== trumpSuit) && (meldCards.get(i+1).charAt(0) == 'Q' && meldCards.get(i+1).charAt(1) == trumpSuit)) {
                meldType = "RoyalMarriage";
            }
            else if ((meldCards.get(i).charAt(0) == 'Q' && meldCards.get(i).charAt(1) == trumpSuit) && (meldCards.get(i+1).charAt(0) == 'K' && meldCards.get(i+1).charAt(1) == trumpSuit)) {
                meldType = "RoyalMarriage";
            }
            else if ((meldCards.get(i).charAt(1) == meldCards.get(i+1).charAt(1)) &&
                    (meldCards.get(i).charAt(0) == 'K' || meldCards.get(i).charAt(0) == 'Q') &&
                    (meldCards.get(i+1).charAt(0) == 'Q' || meldCards.get(i+1).charAt(0) == 'K') &&
                    (meldCards.get(i).charAt(0) != meldCards.get(i+1).charAt(0))) {
                meldType = "Marriage";
            }
            else {
                meldType = "None";
            }
        }

        else if (numCards == 4) {
            boolean isSameFace = true;
            boolean isUniqueSuit = true;
            char cardFace = meldCards.get(0).charAt(0);

            if (cardFace == 'J' || cardFace == 'Q' || cardFace == 'K' || cardFace == 'A') {
                for (int i = 1; i < numCards; i++) {
                    if (meldCards.get(i).charAt(0) != cardFace) {
                        isSameFace = false;
                        meldType = "None";
                        break;
                    }
                }


                for (int i = 0; i < numCards - 1; i++) {
                    for (int j = i + 1; j < numCards; j++) {
                        if (meldCards.get(i).charAt(1) == meldCards.get(j).charAt(1)) {
                            isUniqueSuit = false;
                            meldType = "None";
                            break;
                        }
                    }
                }

                if (isSameFace && isUniqueSuit) {
                    if (cardFace == 'J') {
                        meldType = "FourJacks";
                    }
                    else if (cardFace == 'Q') {
                        meldType = "FourQueens";
                    }
                    else if (cardFace == 'K') {
                        meldType = "FourKings";
                    }
                    else if (cardFace == 'A') {
                        meldType = "FourAces";
                    }
                    else {
                        meldType = "None";

                    }
                }

            }
            else {
                meldType = "None";
            }
        }
        else if (numCards == 5) {
            boolean isAllTrumpSuit = true;
            boolean isAllLegitFlushCards = true;

            for (int i = 0; i < numCards; i++) {
                if (meldCards.get(i).charAt(1) != trumpSuit) {
                    isAllTrumpSuit = false;
                    meldType = "None";
                    break;
                }

            }


            for (int i = 0; i < numCards - 1; i++) {
                for (int j = i + 1; j < numCards; j++) {
                    if ((meldCards.get(i).charAt(0) == meldCards.get(j).charAt(0)) || meldCards.get(i).charAt(0) == '9' || meldCards.get(j).charAt(0) == '9') {
                        isAllLegitFlushCards = false;
                        meldType = "None";
                        break;
                    }
                }
            }

            if (isAllTrumpSuit && isAllLegitFlushCards) {
                meldType = "Flush";
            }
        }
        else {
            meldType = "None";
        }

        return meldType;


    }

    /**
     * Player::getMeldPoints.
     * Helper method to get the points of a meld.
     * @param meldType String the type of meld supplied.
     * @return int the points of the requested meld type
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int getMeldPoints(String meldType){
        if (meldType.equals("Dix"))
        {
            return 10;
        }
        else if (meldType.equals("Marriage"))
        {
            return 20;
        }
        else if (meldType.equals("Pinochle"))
        {
            return 40;
        }
        else if (meldType.equals("RoyalMarriage"))
        {
            return 40;
        }
        else if (meldType.equals("FourJacks"))
        {
            return 40;
        }
        else if (meldType.equals("FourQueens"))
        {
            return 60;
        }
        else if (meldType.equals("FourKings"))
        {
            return 80;
        }
        else if (meldType.equals("FourAces"))
        {
            return 100;
        }
        else if (meldType.equals("Flush"))
        {
            return 150;
        }
        else
        {
            return 0;
        }

    }

    /**
     * Player::validateMeldCards.
     * Helper method to validate a meld and return it's point.
     * @param cards Vector<String> the cards supplied for meld verification.
     * @return int the points of the meld formed by the supplied card
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int validateMeldCards(Vector<String> cards){
        if(cards.size() > 0){
            Boolean hasANewMeldCard = false;
            HashMap<String, Vector<String>> playerMeldMap = getMeldMap();

            for(int k=0;k<cards.size();k++){
                if(!playerMeldMap.containsKey(cards.get(k).toUpperCase())){
                    hasANewMeldCard = true;
                    break;
                }
            }
            int meldPoints = 0;
            Boolean reUsedCards = false;

            if(hasANewMeldCard){
                String meldType = getMeldType(cards,mTrumpCard.getSuit().charAt(0));

                if(!meldType.equals("None")){
                    for(int i=0;i<cards.size();i++){
                        if(playerMeldMap.containsKey(cards.get(i).toUpperCase())){
                            if((playerMeldMap.get(cards.get(i).toUpperCase())).contains(meldType)){
                                reUsedCards = true;
                                meldPoints = 0;
                                break;
                            }
                        }
                    }

                    if(!reUsedCards){
                        meldPoints = getMeldPoints(meldType);
                    }
                }

                else{
                    meldPoints = 0;
                }

                return meldPoints;
            }
            else{
                meldPoints = 0;
            }
        }
        return 0;
    }

    /**
     * Player::bestChaseMove.
     * Helper method to evaluate a player's best chase move.
     * @param leadCardStr String the lead card string
     * @param trumpCard Card the trump card object.
     * @return int the index of the evaluated card in playable pile
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int bestChaseMove(String leadCardStr, Card trumpCard){
        int mostInexpensiveIndex = 0;
        HashMap<Integer,Integer> winningMap = new HashMap<>();
        Card leadCard = new Card(Character.toString(leadCardStr.toUpperCase().charAt(0)) ,Character.toString(leadCardStr.toUpperCase().charAt(1)),Character.getNumericValue(leadCardStr.toUpperCase().charAt(2)) );

        for(int i=0;i<mPlayablePile.size();i++){
            if(mPlayablePile.get(i).getSuit().equals(leadCard.getSuit()) && leadCard.getSuit().equals(trumpCard.getSuit())){
                if(mPlayablePile.get(i).convertFaceToPoints() > leadCard.convertFaceToPoints()){
                    winningMap.put(i,100+mPlayablePile.get(i).convertFaceToPoints());
                }
            }
            else if(mPlayablePile.get(i).getSuit().equals(leadCard.getSuit())){
                if(mPlayablePile.get(i).convertFaceToPoints() > leadCard.convertFaceToPoints()){
                    winningMap.put(i,mPlayablePile.get(i).convertFaceToPoints());
                }
            }
            else if(mPlayablePile.get(i).getSuit().equals(trumpCard.getSuit())){
                winningMap.put(i,100+mPlayablePile.get(i).convertFaceToPoints());
            }
            else {
                continue;
            }
        }

        Vector<Integer> checkCardIndex = new Vector<>();
        if (winningMap.isEmpty()) {
            for(int i=0;i<mPlayablePile.size();i++) {
                checkCardIndex.add(i);
            }
        }
        else {
            checkCardIndex.addAll(winningMap.keySet());
        }
        int maximumMeldPoints = 0;
        HashMap<Integer,Integer> bestWinningChaseCard = new HashMap<>();

        for(int i=0;i<checkCardIndex.size();i++){
            Card checkCard = mPlayablePile.get(checkCardIndex.get(i));
            Vector<Card> tempCheckVec = new Vector<>();

            for(int j=0;j<mPlayablePile.size();j++){
                if(mPlayablePile.get(j) != checkCard){
                    tempCheckVec.add(mPlayablePile.get(j));
                }
            }

            HashMap<String, Vector<Vector<Card>>> possibleMelds = totalPossibleMelds(tempCheckVec);
            int totalMeldPoints = 0;

            for(Map.Entry each: possibleMelds.entrySet()){
                String meldType = each.getKey().toString();
                totalMeldPoints+= (possibleMelds.get(meldType).size()) * getMeldPoints(meldType);

            }

            if(maximumMeldPoints < totalMeldPoints){
                maximumMeldPoints = totalMeldPoints;
            }
            bestWinningChaseCard.put(checkCardIndex.get(i),totalMeldPoints);

        }

        boolean checkBegin = false;
        for(Map.Entry each: bestWinningChaseCard.entrySet()){
            if((int)each.getValue() == maximumMeldPoints){
                if(checkBegin){
                    int prevPoint = mPlayablePile.get(mostInexpensiveIndex).convertFaceToPoints();
                    if(mPlayablePile.get(mostInexpensiveIndex).getSuit().equals(mTrumpCard.getSuit())){
                        prevPoint = 100+prevPoint;
                    }

                    int thisPoint = mPlayablePile.get((int)each.getKey()).convertFaceToPoints();
                    if(mPlayablePile.get((int) each.getKey()).getSuit().equals(mTrumpCard.getSuit())){
                        thisPoint = 100+thisPoint;
                    }

                    if(thisPoint<prevPoint){
                        mostInexpensiveIndex = (int)each.getKey();
                    }
                }
                else{
                    mostInexpensiveIndex = (int)each.getKey();
                    checkBegin = true;
                }
            }
        }

        return mostInexpensiveIndex;
    }

    /**
     * Player::bestChaseMove.
     * Helper method to evaluate a player's best lead move.
     * @return int the index of the evaluated card in playable pile
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int bestLeadMove(){
        int bestPlayableCard = 0;
        int maximumMeldPoints = 0;
        HashMap<Integer,Integer> winningMap = new HashMap<>();

        for(int i=0;i<mPlayablePile.size();i++){
            Card checkCard = mPlayablePile.get(i);
            Vector<Card> tempCheckVec = new Vector<>();

            for(int j=0;j<mPlayablePile.size();j++){
                if(mPlayablePile.get(j) != checkCard){
                    tempCheckVec.add(mPlayablePile.get(j));
                }
            }

            HashMap<String, Vector<Vector<Card>>> possibleMelds = totalPossibleMelds(tempCheckVec);
            int totalMeldPoints = 0;

            for(Map.Entry each: possibleMelds.entrySet()){
                String meldType = each.getKey().toString();
                totalMeldPoints+= (possibleMelds.get(meldType).size()) * getMeldPoints(meldType);

            }

            if(maximumMeldPoints < totalMeldPoints){
                maximumMeldPoints = totalMeldPoints;
            }
            winningMap.put(i,totalMeldPoints);

        }

        boolean checkBegin = false;
        for(Map.Entry each: winningMap.entrySet()){
            if((int)each.getValue() == maximumMeldPoints){
                if(checkBegin){
                    int prevPoint = mPlayablePile.get(bestPlayableCard).convertFaceToPoints();
                    if(mPlayablePile.get(bestPlayableCard).getSuit().equals(mTrumpCard.getSuit())){
                        prevPoint = 100+prevPoint;
                    }

                    int thisPoint = mPlayablePile.get((int)each.getKey()).convertFaceToPoints();
                    if(mPlayablePile.get((int) each.getKey()).getSuit().equals(mTrumpCard.getSuit())){
                        thisPoint = 100+thisPoint;
                    }

                    if(thisPoint>prevPoint){
                        bestPlayableCard = (int)each.getKey();
                    }
                }
                else{
                    bestPlayableCard = (int)each.getKey();
                    checkBegin = true;
                }
            }
        }

        return bestPlayableCard;
    }

    /**
     * Player::totalPossibleMelds.
     * Helper method to evaluate all available melds for a player
     * @param cards Vector<Card> a player's available cards for the meld
     * @return HashMap<String,Vector<Vector<Card>>> a map containing all meld names and cards used to get the meld
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    HashMap<String,Vector<Vector<Card>>> totalPossibleMelds(Vector<Card> cards){

        Vector<Vector<Card>> combos = new Vector<>();
        Vector<Card> tempData = new Vector<>();

        for(int i=1;i<=5;i++){
            if(i!=3) {
                Vector<Vector<Card>> tempCombo = new Vector<>();
                generateAllCardsCombo(tempCombo, cards, tempData, 0 , cards.size()-1, 0, i);
                combos.addAll(tempCombo);
            }
        }

        HashMap<String, Vector<Vector<Card>>> possibleMelds = new HashMap<>();
        for(int j=0;j<combos.size();j++){
            Vector<String> cardVals = new Vector<>();
            for(int i=0;i<combos.get(j).size();i++){
                cardVals.add(combos.get(j).get(i).getFace() + combos.get(j).get(i).getSuit()+combos.get(j).get(i).getCardNum());
            }
            int meldPoint = validateMeldCards(cardVals);

            if(meldPoint>0){
                String meldType = getMeldType(cardVals,mTrumpCard.getSuit().charAt(0));
                Boolean repeatedCard = false;
                if(possibleMelds.containsKey(meldType)){

                    Vector<Vector<Card>> currList = possibleMelds.get(meldType);
                    for(Vector<Card> eachVec:currList){
                        for(Card eachCard: combos.get(j)){
                            if(eachVec.contains(eachCard)){
                                repeatedCard = true;
                                break;
                            }
                        }
                        if(repeatedCard){
                            break;
                        }

                    }

                    if(!repeatedCard) {
                        currList.add(combos.get(j));
                        possibleMelds.put(meldType,currList);
                    }

                }
                else {
                    Vector<Vector<Card>> newList = new Vector<>();
                    newList.add(combos.get(j));
                    possibleMelds.put(meldType,newList);
                }
            }
        }

        return possibleMelds;


    }

    /**
     * Player::generateAllCardsCombo.
     * Helper method to a generate all combinations of a desired number of cards.
     * @param finalVec Vector<Vector<Card>> the vector containing all combos.
     * @param cards Vector<Card> a player's available cards for the combination.
     * @param tempCards Vector<Card> list containing intermediate combinations.
     * @param start int the starting index of the card with reference to which the combination is being generated
     * @param end int the starting index of the card with reference to which the combination is being generated
     * @param index int the current index of the card.
     * @param reqdLen int the number of cards in the required combination
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    static void generateAllCardsCombo(Vector<Vector<Card>> finalVec, Vector<Card> cards, Vector<Card> tempCards, int start, int end, int index, int reqdLen) {

        if (index == reqdLen) {
            Vector<Card> retVec = new Vector<>();
            for (int j=0; j<reqdLen; j++) {
                retVec.add(tempCards.get(j));
            }
            finalVec.add(retVec);
            return;
        }

        for (int i=start; i<=end && end-i+1 >= reqdLen-index; i++) {
            tempCards.add(index, cards.get(i));
            generateAllCardsCombo(finalVec, cards, tempCards, i+1, end, index+1, reqdLen);
        }
    }

    /**
     * Player::loadPlayerScore.
     * Helper method to a load a player's score from a saved file.
     * @param total int the total score of the player in the saved file.
     * @param round int the round score of the player in the saved file.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void loadPlayerScore(int total, int round){
        mTotalScore = total;
        mScore = round;
    }

    /**
     * Player::loadPile.
     * Helper method to a load a player's pile from a saved file.
     * @param cards Vector<String> the cards retrieved from the the saved file.
     * @param allCards Vector<String> all cards that have been created up until that point.
     * @param pile String the name of the pile to be loaded
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void loadPile(Vector<String> cards, Vector<String> allCards, String pile){

        for(int i=0;i<cards.size();i++){
            if(allCards.contains(cards.get(i))) {
                Card newCard = new Card(String.valueOf(cards.get(i).charAt(0)) , String.valueOf(cards.get(i).charAt(1)) , 2 );
                if(pile.equals("hand")){
                    mHandPile.add(newCard);
                    mPlayablePile.add(newCard);
                }else{
                    mCapturePile.add(newCard);
                }

            }
            else {
                Card newCard = new Card(String.valueOf(cards.get(i).charAt(0)) , String.valueOf(cards.get(i).charAt(1)) , 1 );
                allCards.add(newCard.getFace()+newCard.getSuit());
                if(pile.equals("hand")){
                    mHandPile.add(newCard);
                    mPlayablePile.add(newCard);
                }else{
                    mCapturePile.add(newCard);
                }
            }
        }
    }

    /**
     * Player::loadMelds.
     * Helper method to a load a player's meld from a saved file.
     * @param loadedMap HashMap<String, Vector<Vector<String>>> the melds retrieved from the the saved file.
     * @param allCards Vector<String> all cards that have been created up until that point.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void loadMelds(HashMap<String, Vector<Vector<String>>> loadedMap, Vector<String> allCards){

        for(String each : loadedMap.keySet()){

            for(int count1 = 0;count1 < loadedMap.get(each).size(); count1++){
                String meldType = getMeldType(loadedMap.get(each).get(count1),mTrumpCard.getSuit().charAt(0));
                Vector<String> cardsForActiveMelds = new Vector<>();
                for(int count2 = 0; count2 < loadedMap.get(each).get(count1).size(); count2++){

                    if(allCards.contains(loadedMap.get(each).get(count1).get(count2))){
                        String cardStr = loadedMap.get(each).get(count1).get(count2);
                        Card newCard = new Card(String.valueOf(cardStr.charAt(0)), String.valueOf(cardStr.charAt(1)), 2);
                        newCard.setActiveMeldUsedFor(1);
                        if(meldMap.containsKey(newCard.getCardStr())){
                            Vector<String> valueVec = meldMap.get(newCard.getCardStr());
                            valueVec.add(meldType);
                            meldMap.put(newCard.getCardStr(), valueVec);
                        }
                        else{
                            Vector<String> valueVec = new Vector<>();
                            valueVec.add(meldType);
                            meldMap.put(newCard.getCardStr(), valueVec);
                        }
                        mPlayablePile.add(newCard);
                        allCards.add(cardStr);
                        cardsForActiveMelds.add(newCard.getCardStr());
                    }else{
                        String cardStr = loadedMap.get(each).get(count1).get(count2);
                        Card newCard = new Card(String.valueOf(cardStr.charAt(0)), String.valueOf(cardStr.charAt(1)), 1);
                        newCard.setActiveMeldUsedFor(1);
                        if(meldMap.containsKey(newCard.getCardStr())){
                            Vector<String> valueVec = meldMap.get(newCard.getCardStr());
                            valueVec.add(meldType);
                            meldMap.put(newCard.getCardStr(), valueVec);
                        }
                        else{
                            Vector<String> valueVec = new Vector<>();
                            valueVec.add(meldType);
                            meldMap.put(newCard.getCardStr(), valueVec);
                        }
                        mPlayablePile.add(newCard);
                        allCards.add(cardStr);
                        cardsForActiveMelds.add(newCard.getCardStr());
                    }
                }

                if(activeMelds.containsKey(meldType)) {
                    Vector<Vector<String>> meldCards = activeMelds.get(meldType);
                    meldCards.add(cardsForActiveMelds);
                    activeMelds.put(meldType, meldCards);
                }else{
                    Vector<Vector<String>> meldCards = new Vector<>();
                    meldCards.add(cardsForActiveMelds);
                    activeMelds.put(meldType, meldCards);
                }

            }

        }

    }

    /**
     * Player::clearPiles.
     * Helper method to a reset a player's pile and scores for a new round.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void clearPiles(){
        mHandPile.clear();
        mCapturePile.clear();
        mMeldPile.clear();
        mPlayablePile.clear();
        activeMelds.clear();
        meldMap.clear();
        mScore = 0;
    }
}
