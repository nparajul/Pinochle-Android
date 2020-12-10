/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */


package edu.ramapo.nparajul.pinochle.model.setup;

/**
 ************************************************************
 * Card.java
 * Card class to create Card objects. Implements behavior of a card object.
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
public class Card {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************
    private String mFace;
    private String mSuit;
    private int mCardNum;
    private int activeMeldUsedFor;

    /**
     * Card::Card.
     * Card class default constructor.
     * Constructs a card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Card(){}

    /**
     * Card::Card.
     * Card class parameterized constructor.
     * Constructs a card object by initializing member variables.
     * @param face String The face of the card to construct.
     * @param suit String The suit of the card to construct.
    *  @param cardNum int The identifier of the card to construct.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Card(String face, String suit, int cardNum) {
        mFace = face;
        mSuit = suit;
        mCardNum = cardNum;
    }

    /**
     * Card::getFace.
     * Card face accessor.
     * Accessor to get the face of a card object.
     * @return String The face of the card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public String getFace() {
        return mFace;
    }

    /**
     * Card::getSuit.
     * Card suit accessor.
     * Accessor to get the suit of a card object.
     * @return String The suit of the card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public String getSuit() {
        return mSuit;
    }

    /**
     * Card::getCardNum.
     * Card unique identifier accessor.
     * Accessor to get the unique identifier number of a card object.
     * @return int The unique identifier number of the card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int getCardNum() {
        return mCardNum;
    }

    /**
     * Card::getCardStr.
     * Card value in string accessor.
     * Accessor to get the string value of a card object.
     * @return String The string representation of the card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public String getCardStr(){
        return mFace+mSuit+mCardNum;
    }

    /**
     * Card::getActiveMeldUsedFor.
     * Card's number of active melds used for accessor.
     * Accessor to get the number of active melds of a card object.
     * @return int The number of active melds of the card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int getActiveMeldUsedFor() {
        return activeMeldUsedFor;
    }

    /**
     * Card::setActiveMeldUsedFor.
     * Card activeMeldsUsedFor mutator.
     * Mutator to set the number of active melds of a card object.
     * @param activeMeldNums The number of active melds to add to the card.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setActiveMeldUsedFor(int activeMeldNums) {
        activeMeldUsedFor += activeMeldNums;
    }

    /**
     * Card::getCardDisplay.
     * Card description accessor.
     * Method to get the description of a card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public String getCardDisplay(){
        String description = "";
        String faceValue = getFace();

        switch (faceValue) {
            case "9":
                description += "Nine";
                break;
            case "X":
                description += "Ten";
                break;
            case "J":
                description += "Jack";
                break;
            case "Q":
                description += "Queen";
                break;
            case "K":
                description += "King";
                break;
            case "A":
                description += "Ace";
                break;
            default:

        }

        String suitValue = getSuit();
        switch (suitValue) {
            case "H":
                description+=" of Hearts";
                break;
            case "C":
                description+=" of Clubs";
                break;
            case "S":
                description+=" of Spades";
                break;
            case "D":
                description+=" of Diamonds";
                break;
            default:

        }

        return description;
    }

    /**
     * Card::convertFaceToPoints.
     * Card points accessor.
     * Accessor to get the points of a card object.
     * @return int The points of the card object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public int convertFaceToPoints() {
        String faceValue = this.mFace;
        int points = -1;

        if(faceValue.equals("9"))
        {
            points= 0;
        }
        else if (faceValue.equals("X"))
        {
            points= 10;
        }
        else if (faceValue.equals("J"))
        {
            points= 2;
        }
        else if (faceValue.equals("Q"))
        {
            points= 3;
        }
        else if (faceValue.equals("K"))
        {
            points= 4;
        }
        else if (faceValue.equals("A"))
        {
            points= 11;
        }

        return points;
    }

}
