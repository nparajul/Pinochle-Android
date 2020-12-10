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
 * Human.java
 * Human class to create human objects. Implements behavior of a human object.
 * Created by Nitesh Parajuli on 12/08/20.
 * Copyright © 2020 Nitesh Parajuli. All rights reserved.
 ************************************************************
 */
public class Human extends Player {

    /**
     * Human::Human.
     * Human class default constructor.
     * Constructs a human object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Human() {
    }

    /**
     * Human::makeMove.
     * Method to recommend the best human player move.
     * @param leadCardStr String the string representation of the lead card in the turn
     * @param trumpCard trump card object
     * @return Card the recommended human card for the turn
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Card makeMove(String leadCardStr, Card trumpCard){
        int mostInexpensiveIndex = 0;

        if (leadCardStr.isEmpty()) {
            mostInexpensiveIndex = bestLeadMove();
        }
        else {
            mostInexpensiveIndex = bestChaseMove(leadCardStr, trumpCard);
        }

        return mPlayablePile.get(mostInexpensiveIndex);
    }

    /**
     * Human::declareMeld.
     * Method to recommend the best human player meld.
     * @return Vector<String> list of cards in the best human meld.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> declareMeld () {
        HashMap<String, Vector<Vector<Card>>> allValidMelds = totalPossibleMelds(mPlayablePile);
        Vector<String> meldCardStr = new Vector<>();

        int maxMeldPoints = 0;

        if(!allValidMelds.isEmpty()){
            Vector<Card> meldCards = new Vector<>();
            String meldType = "";
            for(Map.Entry each : allValidMelds.entrySet()){
                int meldPoints = getMeldPoints((String) each.getKey());

                if(meldPoints >= maxMeldPoints){
                    maxMeldPoints = meldPoints;
                    meldType = (String)each.getKey();
                }
            }

            meldCards = allValidMelds.get(meldType).get(0);

            for(int i=0;i<meldCards.size();i++){
                meldCardStr.add(meldCards.get(i).getCardStr());
            }

        }
        else {
            meldCardStr = null;
        }

        return meldCardStr;
    }

}
