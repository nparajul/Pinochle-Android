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
 * Computer.java
 * Computer class to create computer objects. Implements behavior of a computer object.
 * Created by Nitesh Parajuli on 12/08/20.
 * Copyright © 2020 Nitesh Parajuli. All rights reserved.
 ************************************************************
 */
public class Computer extends Player {
    /**
     * Computer::Computer.
     * Human class default constructor.
     * Constructs a human object.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Computer() {
    }

    /**
     * Computer::makeMove.
     * Method to make the best computer player move.
     * @param leadCardStr String the string representation of the lead card in the turn
     * @param trumpCard trump card object
     * @return Card the card played by computer for the turn
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Card makeMove(String leadCardStr, Card trumpCard) {

        int mostInexpensiveIndex = 0;

        if (leadCardStr.isEmpty()) {
            mostInexpensiveIndex = bestLeadMove();
        }
        else {
            mostInexpensiveIndex = bestChaseMove(leadCardStr, trumpCard);
        }
        Card playedCard = mPlayablePile.get(mostInexpensiveIndex);
        removeCardFromPile(playedCard);
        this.latestPlayed = playedCard;
        return playedCard;
    }


    /**
     * Computer::declareMeld.
     * Method to declare the best computer player meld.
     * @return Vector<String> list of cards in the meld declared by the computer.
     * @author Nitesh Parajuli
     * @date 12/08/20.
     *
     */
    public Vector<String> declareMeld (){

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
                updateMeldMap(meldCards.get(i).getCardStr(), meldType);
                meldCardStr.add(meldCards.get(i).getCardStr());

            }

            if(activeMelds.containsKey(meldType)){
                Vector<Vector<String>> currList = activeMelds.get(meldType);
                currList.add(meldCardStr);
                activeMelds.put(meldType,currList);

            }
            else {
                Vector<Vector<String>> newList = new Vector<>();
                newList.add(meldCardStr);
                activeMelds.put(meldType,newList);
            }


           for(int i=0;i<mPlayablePile.size();i++){
               for(int j=0;j<meldCards.size();j++){
                   if(mPlayablePile.get(i).getCardStr().equals(meldCards.get(j).getCardStr())){
                       mPlayablePile.get(i).setActiveMeldUsedFor(1);
                   }
               }
           }



        }
        else
        {
            meldCardStr = null;
        }

        updatePlayerScore(maxMeldPoints);
        return meldCardStr;
    }

}
