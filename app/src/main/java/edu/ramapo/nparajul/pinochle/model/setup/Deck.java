/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */
package edu.ramapo.nparajul.pinochle.model.setup;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

/**
 ************************************************************
 * Deck.java
 * Deck class to create a deck of cards. Implements behavior of a deck object. Uses Card class to create cards.
 *
 * Member Variables:
 *       mDeck - Vector of cards to record the deck of cards.
 *       mTopCardPos - Index of the top card in the deck.
 * Created by Nitesh Parajuli on 12/08/20.
 *
 * Copyright © 2020 Nitesh Parajuli. All rights reserved.
 ************************************************************
 */
public class Deck {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************
    private Vector<Card> mDeck = new Vector<>();
    private int mTopCardPos;

    /**
     * Deck::Deck.
     * Deck class default constructor.
     * Constructs a deck object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Deck(){


    }


    /**
     * Deck::createNewDeck. Method to create a deck of cards.
     * Method to create a new deck of cards.
     * @author Nitesh Parajuli.
     * @date 12/08/20.
     */
    public void createNewDeck(){

        String[] faces = { "9","X","J","Q","K","A" };
        String[] suits = { "S","H","D","C" };
        mTopCardPos = 0;

        for(int i=0;i<2;i++)
        {
            for(int j=0;j < (suits.length); j++ )
            {
                for(int k=0;k<(faces.length); k++)
                {
                    Card newCard = new Card (faces[k],suits[j],i+1);
                    mDeck.add(newCard);
                }
            }
        }

        shuffleDeck();

    }

    /**
     * Deck::shuffleDeck. Method to shuffle cards in a deck.
     * Method to shuffle a deck of cards.
     * @author Nitesh Parajuli.
     * @date 12/08/20.
     */
    public void shuffleDeck(){
        Collections.shuffle(mDeck, new Random(System.currentTimeMillis()));
    }

    /**
     * Deck::getDeck. Method to get deck with string values of card object.
     * Accessor method to get the deck with string values of card object.
     * @return Vector<String> The deck object with card values in string.
     * @author Nitesh Parajuli.
     * @date 12/08/20.
     */
    public Vector<String> getDeck(){
        Vector<String> returnDeck = new Vector<String>();
        for(int i = mTopCardPos;i < mDeck.size(); i++){
            returnDeck.add(mDeck.get(i).getSuit().toLowerCase() + mDeck.get(i).getFace().toLowerCase()  + mDeck.get(i).getCardNum());
        }
        return returnDeck;
    }

    /**
     * Deck::getCardDeck. Method to get deck with Card objects.
     * Accessor method to get the deck with Card Objects.
     * @return Vector<Card> The deck object with card objects.
     * @author Nitesh Parajuli.
     * @date 12/08/20.
     */
    public Vector<Card> getCardDeck(){
        Vector<Card> deck = new Vector<>();
        for(int i = mTopCardPos;i < mDeck.size(); i++){
            deck.add(mDeck.get(i));
        }
        return deck;
    }

    /**
     * Deck::shuffleDeck. Method to draw a card from a deck.
     * Method to draw next card from the deck of cards.
     * @return Card The drawn card object from the deck.
     * @author Nitesh Parajuli.
     * @date 12/08/20.
     */
    public Card drawCard(){
        Card drawnCard = mDeck.get(mTopCardPos);
        mTopCardPos++;
        return drawnCard;

    }

    /**
     * Deck::createNewDeck. Method to load a deck.
     * Method to load a deck from a saved file.
     * @param cards Vector<String> values of cards in the deck stored in the saved file.
     * @param allCards Vector<String> All cards that have been used before creating the deck while loading a game.
     * @author Nitesh Parajuli.
     * @date 12/08/20.
     */
    public void loadDeck(Vector<String> cards, Vector<String> allCards){
        for(int i=0;i<cards.size();i++){
            if(allCards.contains(cards.get(i))){
                mDeck.add(new Card(String.valueOf(cards.get(i).charAt(0)), String.valueOf(cards.get(i).charAt(1)), 2));
            }
            else {
                mDeck.add(new Card(String.valueOf(cards.get(i).charAt(0)), String.valueOf(cards.get(i).charAt(1)), 1));
                allCards.add(cards.get(i));
            }
        }
    }

}
