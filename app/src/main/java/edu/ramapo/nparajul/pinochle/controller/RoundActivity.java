/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */

package edu.ramapo.nparajul.pinochle.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import edu.ramapo.nparajul.pinochle.R;
import edu.ramapo.nparajul.pinochle.model.setup.Card;
import edu.ramapo.nparajul.pinochle.model.setup.Game;

public class RoundActivity extends AppCompatActivity implements  PlayDialog.PlayDialogListener {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************

    // lead player in the turn
    private String lead_player ;

    // next player
    private String next_player ;

    //Game state. 0 for turn, 1 for meld
    private int state = 0;

    //Game object
    private Game mGame = new Game();

    //TextView to display human points
    private TextView humanPoints;

    //TextView to display computer points
    private TextView computerPoints;

    //Button for human help
    private Button helpButton;

    //game logs button
    private Button logButton;

    //imageview for displaying cards
    private ImageView imageview;

    //Stock Pile layout
    private LinearLayout mStockpileZone;

    //play Turn button
    private Button playButton;

    //declare meld Button
    private Button meldButton;

    //human capture pile button
    private Button humanCapturePileBtn;

    //computer capture pile button
    private Button computerCapturePileBtn;

    //save game button
    private Button saveButton;

    //exitgame button
    private Button quitButton;

    //textview to display computer's move
    private TextView screen_log;

    //human cards display area
    LinearLayout mHumanZone;

    //computer cards display area
    LinearLayout mComputerZone;

    //human meld display zone
    LinearLayout mHumanMeldZone;

    //computer meld display zone
    LinearLayout mComputerMeldZone;

    //textview to display human pile name
    TextView humanPileName;

    //textview to display computer pile name
    TextView computerPileName;

    //arraylist to store game logs
    ArrayList<String> gameLogs = new ArrayList<>();

    // *********************************************************
    // ********************* Constructors **********************
    // *********************************************************

    /**
     * RoundActivity::onCreate
     * sets onClick listeners for all the buttons in this activity
     * loads the game according to the intent parameters passed from the main activity
     * @author Nitesh Parajuli
     * @date 12/08/20
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        //get all view elements
        humanPoints = findViewById(R.id.human_points_textview);
        computerPoints = findViewById(R.id.computer_points_textview);
        helpButton = findViewById(R.id.get_help_button);
        mStockpileZone = findViewById(R.id.stock_pile_linearlayout);
        playButton = findViewById(R.id.play_button);
        helpButton = findViewById(R.id.get_help_button);
        meldButton = findViewById(R.id.declare_meld_button);
        humanCapturePileBtn = findViewById(R.id.humanCaptureButton);
        computerCapturePileBtn = findViewById(R.id.computerCapturebutton);
        mHumanZone = findViewById(R.id.human_hand_linearlayout);
        mComputerZone = findViewById(R.id.computer_hand_linearlayout);
        humanPileName = findViewById(R.id.humanHandText);
        computerPileName = findViewById(R.id.computerHandText);
        mHumanMeldZone = findViewById(R.id.human_meld_linearlayout);
        mComputerMeldZone = findViewById(R.id.computer_melds_linearlayout);
        screen_log = findViewById(R.id.logView);
        logButton = findViewById(R.id.log_button);
        saveButton = findViewById(R.id.save_game_button);
        quitButton = findViewById(R.id.quitButton);

        //get intent values passed from main activity
        Intent intent = getIntent();
        boolean newRound = intent.getBooleanExtra("newRound",true);

        //new round
        if(newRound) {
            setUpNewRound();
        }
        // load Round
        else {
            String filePath = intent.getStringExtra("file_name");
            setUpLoadRound(filePath);
        }

        //On click listeners for the buttons

        // display game logs
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBoxBuilder = new AlertDialog.Builder(RoundActivity.this);
                alertBoxBuilder.setCancelable(true);
                alertBoxBuilder.setTitle("Game Log");
                String allLogs = "";
                for(int i=gameLogs.size()-1;i>=0;i--){
                    allLogs += gameLogs.get(i)+"\n\n";
                }
                alertBoxBuilder.setMessage(allLogs);
                alertBoxBuilder.setPositiveButton("Okay!", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                alertBoxBuilder.show();
            }
        });

        //play a turn
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(next_player.equals("Human")) {
                    openPlayDialog();
                }
                if(next_player.equals("Computer") && lead_player.equals("Computer")){
                    makeComputerMove();
                }
            }
        });

        //declare melds
        meldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayDialog();
            }
        });

        //display human piles
        humanCapturePileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(humanCapturePileBtn.getText().equals("Capture Pile")) {
                    Vector<String> humanCapture = mGame.getRound().getCaptureCards(0);
                    mHumanZone.removeAllViews();
                    addCards(mHumanZone, humanCapture);
                    humanCapturePileBtn.setText("Hand Pile");
                    humanPileName.setText("Capture:");
                }
                else {
                    Vector<String> humanHand = mGame.getRound().getHandCards(0);
                    mHumanZone.removeAllViews();
                    addCards(mHumanZone, humanHand);
                    humanCapturePileBtn.setText("Capture Pile");
                    humanPileName.setText("Hand:");
                }
            }
        });

        //exit the game
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBoxBuilder = new AlertDialog.Builder(RoundActivity.this);
                alertBoxBuilder.setCancelable(true);
                alertBoxBuilder.setTitle("Exit Game");
                alertBoxBuilder.setMessage("Are you sure you want to quit?");
                alertBoxBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                });
                alertBoxBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                alertBoxBuilder.show();
            }
        });

        //display computer piles
        computerCapturePileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(computerCapturePileBtn.getText().equals("Capture Pile")) {
                    Vector<String> computerCapture = mGame.getRound().getCaptureCards(1);
                    mComputerZone.removeAllViews();
                    addCards(mComputerZone, computerCapture);
                    computerCapturePileBtn.setText("Hand Pile");
                    computerPileName.setText("Capture:");
                }
                else
                {
                    Vector<String> computerHand = mGame.getRound().getHandCards(1);
                    mComputerZone.removeAllViews();
                    addCards(mComputerZone, computerHand);
                    computerCapturePileBtn.setText("Capture Pile");
                    computerPileName.setText("Hand:");
                }
            }
        });


        //display help for human player
         helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state==0) {
                    if (lead_player.equals("Human")) {
                        Card recommended = mGame.getRound().getPlayer(0).makeMove("", mGame.getRound().getTrump());
                        String log = "Computer recommends you play " + recommended.getCardDisplay() + " because it is the best card in hand after meld consideration.";
                        showRecommendationAlertBox(log);
                    } else {
                        Card recommended = mGame.getRound().getPlayer(0).makeMove(mGame.getRound().getPlayer(1).getLatestPlayed().getCardStr(), mGame.getRound().getTrump());
                        String log = "Computer recommends you play " + recommended.getCardDisplay() + " because ";
                        if(mGame.getRound().getTurnWinner(mGame.getRound().getPlayer(1).getLatestPlayed(), recommended, 1, 0, true) == 0){
                            log += " you could win the turn.";
                        }
                        else
                        {
                            log += " you could not win the turn.";
                        }
                        showRecommendationAlertBox(log);
                    }
                }

                if(state == 1){
                    Vector<String> meldCardStr = mGame.getRound().getPlayer(0).declareMeld();

                    if(meldCardStr == null){
                        String log = "Human does not have a meld to declare for this turn.";
                        gameLogs.add(log);
                        showRecommendationAlertBox(log);
                    }
                    else {

                        String log = "Computer recommends a ";
                        String meldType = mGame.getRound().getPlayer(0).getMeldType(meldCardStr, mGame.getRound().getTrump().getSuit().charAt(0));
                        log += meldType + " meld with the following cards: ";
                        for(int i=0;i<meldCardStr.size();i++){
                            log += String.valueOf(meldCardStr.get(i).charAt(0)) + String.valueOf(meldCardStr.get(i).charAt(1)) +" ";

                        }
                        log+= "and earn " + mGame.getRound().getPlayer(0).getMeldPoints(meldType) + " points.";
                        showRecommendationAlertBox(log);

                    }
                }
            }
        });

         //save the game
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String writeDetails = "Round: 1"+"\n\n";
                writeDetails += "Computer:"+ "\n";
                writeDetails += getSerializeInfo(1);
                writeDetails += "\nHuman:" + "\n";
                writeDetails += getSerializeInfo(0);
                writeDetails += "\nTrump Card: "+ mGame.getRound().getTrump().getFace()+mGame.getRound().getTrump().getSuit() + "\n";
                writeDetails += "Stock: ";
                Vector<Card> stockPile = mGame.getRound().stockDeck().getCardDeck();
                for(int i=0;i<stockPile.size();i++){
                    String card = stockPile.get(i).getFace()+stockPile.get(i).getSuit()+" ";
                    writeDetails +=  card;
                }
                writeDetails += "\n\n";

                writeDetails += "Next Player: ";
                writeDetails += next_player;
                writeDetails += "\n";

                String saved_files = Environment.getExternalStorageDirectory().getAbsolutePath()+"/saveFiles";
                String fileName ="savedGame.txt";
                File file = new File(saved_files, fileName);

                try{
                    FileOutputStream writeToFile = openFileOutput(fileName, MODE_PRIVATE);
                    writeToFile.write(writeDetails.getBytes());
                    writeToFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                System.exit(1);
            }
        });

    }

    /**
     * RoundActivity::setUpNewRound
     * Method to setup a new round
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void setUpNewRound() {
        final AlertDialog.Builder coin_toss_popup = new AlertDialog.Builder(RoundActivity.this);
        coin_toss_popup.setTitle("COIN TOSS");
        final String[] toss_options = {"Heads", "Tails"};
        final String[] tossCall = {""};


        coin_toss_popup.setItems(toss_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ListView options = ((AlertDialog)dialog).getListView();
                String user_cointoss_selection = (String) options.getAdapter().getItem(which);
                String toss_winner = mGame.tossWinner(user_cointoss_selection);
                lead_player = toss_winner;
                next_player = toss_winner;
                tossCall[0] = "";
                if(toss_winner.equals("Human")){
                    tossCall[0] = user_cointoss_selection;
                }
                else{
                    for(String call: toss_options){
                        if(!call.equals(user_cointoss_selection)){
                            tossCall[0] = call;
                        }
                    }
                }


                gameLogs.add("Toss Result: "+ tossCall[0] +"\n"+ lead_player+ " won the toss and will play first!");
                new AlertDialog.Builder(RoundActivity.this)
                        .setTitle("Toss Result")
                        .setMessage("Toss Result: "+ tossCall[0] +"\n"+ lead_player+ " won the toss and will play first!")

                        .setPositiveButton("Start Game!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface innerDialog, int which) {
                                innerDialog.cancel();
                                mGame.play();
                                if(lead_player.equals("Human")){
                                    makeHumanMove();
                                }else{
                                    makeComputerMove();
                                }
                            }
                        }).show();

            }
        });

        coin_toss_popup.show();

    }

    /**
     * RoundActivity::setUpLoadRound
     * Method to setup a load round
     * @param path String filePath of the saved file
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void setUpLoadRound(String path){
        mGame.load();
        try{
            InputStream is = new FileInputStream(path);
            InputStream isCopy = new FileInputStream(path);
            loadGameDetails(is, isCopy);
            getRoundDetails();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * RoundActivity::openPlayDialog
     * Method to open the play dialog window in human's turn
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void openPlayDialog(){
        PlayDialog playDialog = new PlayDialog();
        Bundle args = new Bundle();
        args.putString("player",next_player);
        args.putInt("state", state);

        Vector<String> cardsToShow = mGame.getRound().getPlayableCards(0);
        ArrayList<String> passArrayList = new ArrayList<String>(cardsToShow);
        args.putStringArrayList("cards", passArrayList);
        playDialog.setArguments(args);
        playDialog.show(getSupportFragmentManager(), "Choose Cards");

    }

    /**
     * RoundActivity::getSerializeInfo
     * Method to get a player's piles and scores for serialization
     * @param playerIndex int player's index
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public String getSerializeInfo(int playerIndex){

        String writeDetails = "\tScore: " + mGame.getRound().getPlayer(playerIndex).getTotalScore()+ " / " + mGame.getRound().getPlayer(playerIndex).getScore()+"\n";
        writeDetails += "\tHand: ";

        Vector<Card> hand = mGame.getRound().getPlayer(playerIndex).getHandPile();
        for(int i=0;i<hand.size();i++){
            String card = hand.get(i).getFace()+hand.get(i).getSuit()+" ";
            writeDetails += card;
        }
        writeDetails += "\n";

        writeDetails += "\tCapture Pile: ";

        Vector<Card> capture = mGame.getRound().getPlayer(playerIndex).getCapturePile();
        for(int i=0;i<capture.size();i++){
            String card = capture.get(i).getFace()+capture.get(i).getSuit()+" ";
            writeDetails += card;
        }
        writeDetails += "\n";

        writeDetails += "\tMelds: ";

        HashMap<String, Vector<Vector<String>>> melds = mGame.getRound().getPlayer(playerIndex).getActiveMelds();
        for(String each:melds.keySet()){
            Vector<Vector<String>> meld= melds.get(each);
            for(int i=0;i<meld.size();i++){
                for(int j=0;j<meld.get(i).size();j++){
                    String meldCard = meld.get(i).get(j);
                    meldCard = meldCard.substring(0, meldCard.length()-1);
                    writeDetails += meldCard;
                    writeDetails += " ";
                }

                if(i!=meld.size()-1){
                    writeDetails += ", ";
                }
            }
        }

        writeDetails += "\n";
        return writeDetails;
    }


    /**
     * RoundActivity::humanSelectedCards
     * Method to get cards selected by the human in a turn or meld
     * @param selectedCards Arraylist<String> list containing the ids of selected card Images
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    @Override
    public void humanSelectedCards(ArrayList<String> selectedCards) {

        if(next_player.equals("Human") && state == 0) {
            Card chosenCard = mGame.getRound().getPlayer(0).getCard(selectedCards.get(0));
            mGame.getRound().getPlayer(0).removeCardFromPile(chosenCard);
            mGame.getRound().getPlayer(0).setLatestPlayed(chosenCard);
            String log = "Human chose " + chosenCard.getCardDisplay();
            gameLogs.add(log);
            next_player = "Computer";
            helpButton.setEnabled(false);

            if(lead_player.equals("Human")) {
                makeComputerMove();
            } else {
                setUpNextTurn(1,0);
            }

        }


        else if(next_player.equals("Human") && state == 1) {
            Vector<Card> meldCards  = new Vector<>();
            Vector<String> cards  = new Vector<>();
            for(int i=0;i<selectedCards.size();i++) {
                Card chosenCard = mGame.getRound().getPlayer(0).getCard(selectedCards.get(i));
                meldCards.add(chosenCard);
                String chosenCardStr = chosenCard.getFace() + chosenCard.getSuit() + chosenCard.getCardNum();
                cards.add(chosenCardStr.toUpperCase());
            }

            int meldPoints = mGame.getRound().getPlayer(0).validateMeldCards(cards);

            if(meldPoints>0) {

                String meldType = mGame.getRound().getPlayer(0).getMeldType(cards, mGame.getRound().getTrumpCard().get(0).toUpperCase().charAt(0));
                for (int i = 0; i < cards.size(); i++) {
                    mGame.getRound().getPlayer(0).updateMeldMap(cards.get(i), meldType);
                    mGame.getRound().getPlayer(0).setActiveMeldsNum(cards.get(i),1);
                }


                mGame.getRound().getPlayer(0).setActiveMelds(meldType,cards);
                String log ="Human declared a "+meldType+" meld with the following cards: ";

                for(int i=0;i<meldCards.size();i++){
                    log += meldCards.get(i).getFace()+meldCards.get(i).getSuit()+" ";
                }
                log+="and earned " + mGame.getRound().getPlayer(0).getMeldPoints(meldType) + " points" ;
                gameLogs.add(log);
                mGame.getRound().getPlayer(0).updatePlayerScore(meldPoints);
                state=0;
                playButton.setEnabled(true);
                meldButton.setEnabled(false);
                mGame.getRound().setupNextTurn(0,1);
                String newLog = "Human's Turn!";
                gameLogs.add(newLog);
                screen_log.setText("");
                makeHumanMove();
            }
            else{
                String newLog = "That's not a valid meld! Please redeclare the meld.";
                screen_log.setText(newLog);
            }

        }
    }

    /**
     * RoundActivity::noMeld
     * Method to setup the game when human does not want to declare a meld
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    @Override
    public void noMeld() {

        String log="Human does not want to declare any melds for this turn.";
        gameLogs.add(log);
        screen_log.setText("");
        state = 0;
        playButton.setEnabled(true);
        meldButton.setEnabled(false);

        if(next_player.equals("Human")){
            mGame.getRound().setupNextTurn(0,1);
            makeHumanMove();
        }
        else{
            mGame.getRound().setupNextTurn(1,0);
            makeComputerMove();
        }

    }

    /**
     * RoundActivity::showRecommendationAlertBox
     * Method to display human help recommended cards and melds
     * @param msg String recommended move for human
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void showRecommendationAlertBox(String msg){
        AlertDialog.Builder alertBoxBuilder = new AlertDialog.Builder(RoundActivity.this);
        alertBoxBuilder.setCancelable(true);
        alertBoxBuilder.setTitle("Computer's Recommendation");
        alertBoxBuilder.setMessage(msg);
        alertBoxBuilder.setPositiveButton("Thanks!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBoxBuilder.show();
    }

    /**
     * RoundActivity::makeHumanMove
     * Method to setup human buttons and let human play a turn
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void makeHumanMove() {
        if(state==0){
            playButton.setEnabled(true);
            meldButton.setEnabled(false);
            playButton.setVisibility(View.VISIBLE);
            meldButton.setVisibility(View.GONE);
            playButton.setText("Play Human's Turn");
        }

        if(state==1){
            playButton.setEnabled(false);
            meldButton.setEnabled(true);
            meldButton.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.GONE);
            meldButton.setText("Declare Meld");

        }
        helpButton.setVisibility(View.VISIBLE);
        helpButton.setEnabled(true);
        getRoundDetails();
    }

    /**
     * RoundActivity::makeComputerMove
     * Method to setup computer buttons, play computer's turn, and decalre computer melds
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void makeComputerMove() {
        meldButton.setVisibility(View.GONE);
        helpButton.setVisibility(View.GONE);
        playButton.setText("Process Computer's Turn");
        getRoundDetails();
        if(state == 0) {
            if (lead_player.equals("Human")) {
                Card computerPlayedCard = mGame.getRound().getPlayer(1).makeMove(mGame.getRound().getPlayer(0).getLatestPlayed().getCardStr(), mGame.getRound().getTrump());
                String log = "Computer played " + computerPlayedCard.getCardDisplay() +" because" ;
                if(mGame.getRound().getTurnWinner(mGame.getRound().getPlayer(0).getLatestPlayed(), computerPlayedCard, 0, 1, true) == 1){
                    log += " it could win the turn.";
                } else {
                    log += " it could not win the turn.";
                }
                gameLogs.add(log);
                screen_log.setText(log);
                setUpNextTurn(0, 1);
            } else {
                Card computerPlayedCard = mGame.getRound().getPlayer(1).makeMove("", mGame.getRound().getTrump());
                String log = "Computer played " + computerPlayedCard.getCardDisplay() + " because it is the strongest card after meld consideration.";
                gameLogs.add(log);
                screen_log.setText(log);
                next_player = "Human";
                makeHumanMove();
            }
        } else{

            Vector<String> meldCards = mGame.getRound().getPlayer(1).declareMeld();

            if(meldCards != null) {
                String meldType = mGame.getRound().getPlayer(1).getMeldType(meldCards, mGame.getRound().getTrump().getSuit().charAt(0));

                String log ="Computer declared a "+meldType+" meld with the following cards: ";
                for(int i=0;i<meldCards.size();i++){
                    log += String.valueOf(meldCards.get(i).charAt(0)) + String.valueOf(meldCards.get(i).charAt(1)) +" ";
                }
                log+="and earned " + mGame.getRound().getPlayer(0).getMeldPoints(meldType) + " points" ;
                gameLogs.add(log);
                screen_log.setText(log);

            }
            else{
                String log="Computer does not have any melds for this turn.";
                gameLogs.add(log);
                screen_log.setText(log);
            }

            state = 0;
            mGame.getRound().setupNextTurn(1,0);
            getRoundDetails();
            String newLog = "Computer's Turn!";
            gameLogs.add(newLog);
            getRoundDetails();
            playButton.setText("Process Computer's Turn");
            meldButton.setVisibility(View.GONE);

        }


    }

    /**
     * RoundActivity::setUpNextTurn
     * Method to setup the game state for next turn
     * @param leadIndex int index of lead player
     * @param chaseIndex int index of the chase player
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void setUpNextTurn(int leadIndex, int chaseIndex){
        Card leadMove = mGame.getRound().getPlayer(leadIndex).getLatestPlayed();
        Card chaseMove = mGame.getRound().getPlayer(chaseIndex).getLatestPlayed();
        int winner = mGame.getRound().getTurnWinner(leadMove,chaseMove,leadIndex, chaseIndex, false);
        state=1;
        if(winner == 0) {
            String log="Human won the turn";
            gameLogs.add(log);
            next_player = "Human";
            lead_player = "Human";

            if(mGame.getRound().roundEnd()){
                endRound();
            }
            else {
                makeHumanMove();
            }
        }
        else {
            String log ="Computer won the turn" ;
            gameLogs.add(log);
            next_player = "Computer";
            lead_player = "Computer";

            if(mGame.getRound().roundEnd()){
                endRound();
            }
            else {
                makeComputerMove();
            }
        }
    }

    /**
     * RoundActivity::getRoundDetails
     * Method to refresh the string after changes in layout data
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void getRoundDetails(){
        Vector<String> humanHand = mGame.getRound().getHandCards(0);
        Vector<String> computerHand = mGame.getRound().getHandCards(1);
        Vector<String> stockPile = mGame.getRound().getStockPile();
        Vector<String> trumpCard = mGame.getRound().getTrumpCard();

        humanPoints.setText("Round: "+ mGame.getRoundNum()  +"\t" + "Human Score:  "+ mGame.getRound().getPlayer(0).getTotalScore() + "/"+ mGame.getRound().getPlayer(0).getScore());
        computerPoints.setText("Computer Score: "+mGame.getRound().getPlayer(1).getTotalScore() +  "/"+ mGame.getRound().getPlayer(1).getScore());
        mHumanZone.removeAllViews();
        addCards(mHumanZone,humanHand);

        mHumanMeldZone.removeAllViews();
        HashMap<String, Vector<Vector<String>>> humanActiveMelds = mGame.getRound().getPlayer(0).getActiveMelds();

        for(String meld: humanActiveMelds.keySet()) {
            for(int i = 0; i< humanActiveMelds.get(meld).size();i++){
                addCardsToMeld(mHumanMeldZone, humanActiveMelds.get(meld).get(i), 0);
            }
        }

        mComputerZone.removeAllViews();
        addCards(mComputerZone,computerHand);

        mComputerMeldZone.removeAllViews();
        HashMap<String, Vector<Vector<String>>> computerActiveMelds = mGame.getRound().getPlayer(1).getActiveMelds();

        for(String meld: computerActiveMelds.keySet()) {
            for(int i = 0; i< computerActiveMelds.get(meld).size();i++){
                addCardsToMeld(mComputerMeldZone, computerActiveMelds.get(meld).get(i), 1);
            }
        }

        LinearLayout trumpLayout = findViewById(R.id.trump_card_layout);
        trumpLayout.removeAllViews();
        addCards(trumpLayout,trumpCard);

        LinearLayout mStockPileZone = findViewById(R.id.stock_pile_linearlayout);
        mStockPileZone.removeAllViews();
        addCards(mStockPileZone,stockPile);

    }

    /**
     * RoundActivity::addCards
     * Method to add cards to a pile layout
     * @param linearLayout LinearLayout the layout where cards are displayed
     * @param cards Vector<String> vector containing all cards to display
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void addCards(LinearLayout linearLayout, Vector<String> cards){

        for(String card:cards)
        {
            imageview = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150,150);
            layoutParams.setMargins(0,5,0,5);
            imageview.setLayoutParams(layoutParams);

            Context context = linearLayout.getContext();
            int id = context.getResources().getIdentifier(card, "drawable", context.getPackageName());
            imageview.setImageResource(id);

            imageview.setTag(card);
            imageview.setPadding(0,5,0,5);
            imageview.setId(id);

            linearLayout.addView(imageview);
        }
    }

    /**
     * RoundActivity::addCardsToMeld
     * Method to add cards to a meld layout
     * @param linearLayout LinearLayout the layout where cards are displayed
     * @param cards Vector<String> vector containing all cards to display
     * @param player int the index of the player
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void addCardsToMeld(LinearLayout linearLayout, Vector<String> cards, int player){

        for(String card:cards)
        {
            card = card.toLowerCase();
            String cardRevised = String.valueOf(card.charAt(1)) + String.valueOf(card.charAt(0)) + String.valueOf(card.charAt(2));
            Card chosenCard = mGame.getRound().getPlayer(player).getCard(cardRevised);
            imageview = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150,150);
            layoutParams.setMargins(5,5,0,5);
            imageview.setLayoutParams(layoutParams);
            Context context = linearLayout.getContext();
            int id = context.getResources().getIdentifier(cardRevised, "drawable", context.getPackageName());
            imageview.setImageResource(id);
            imageview.setTag(cardRevised);
            if(chosenCard.getActiveMeldUsedFor() > 1){
                Drawable selectedCard = getResources().getDrawable(R.drawable.selectedcard);
                imageview.setBackground(selectedCard);
            }
            imageview.setPadding(5,5,0,5);
            imageview.setId(id);

            linearLayout.addView(imageview);
        }

        imageview = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10,190);
        layoutParams.setMargins(5,5,5,5);
        imageview.setLayoutParams(layoutParams);
        Context context = linearLayout.getContext();
        int id = context.getResources().getIdentifier("melddivide", "drawable", context.getPackageName());
        imageview.setImageResource(id);

        imageview.setTag("melddivide");
        imageview.setId(id);
        linearLayout.addView(imageview);

    }

    /**
     * RoundActivity::loadGameDetails
     * Method to add cards to a meld layout
     * @param is InputStream input stream object that holds the file details - main
     * @param isCopy InputStream input stream object that holds the file details - copy
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void loadGameDetails(InputStream is, InputStream isCopy) throws IOException {

        int roundNum = 0, computerTotalScore = 0, computerRoundScore = 0;
        int humanTotalScore = 0, humanRoundScore = 0;
        String buffer = "";
        Vector<String> compHand = new Vector<>();
        Vector<String> compCapture = new Vector<>();
        Vector<String> humanHand = new Vector<>();
        Vector<String> humanCapture = new Vector<>();
        Vector<String> stockPile = new Vector<>();
        HashMap<String, Vector<Vector<String>>> humanActiveMelds = new HashMap<>();
        HashMap<String, Vector<Vector<String>>> compActiveMelds = new HashMap<>();

        String trump = "";

        BufferedReader trumpReader = new BufferedReader(new InputStreamReader(isCopy));
        String trumpLine = trumpReader.readLine();
        Vector<String> allCards = new Vector<>();

        while(trumpLine != null) {
            if (trumpLine.contains("Trump Card")) {
                trump = trumpLine.split(":")[1].trim();
            }
            trumpLine = trumpReader.readLine();
        }

        if(trump.length()==1){
            Card newCard = new Card("n",String.valueOf(trump.charAt(0)),1 );
            mGame.getRound().setTrumpCard(newCard);
            mGame.getRound().getPlayer(0).setTrumpCard(newCard);
            mGame.getRound().getPlayer(1).setTrumpCard(newCard);
        }
        else{
            Card newCard = new Card(String.valueOf(trump.charAt(0)),String.valueOf(trump.charAt(1)),1 );
            mGame.getRound().setTrumpCard(newCard);
            mGame.getRound().getPlayer(0).setTrumpCard(newCard);
            mGame.getRound().getPlayer(1).setTrumpCard(newCard);
            allCards.add(trump);
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();

        while (line != null) {
            if (line.contains("Round")) {
                roundNum = Integer.parseInt(line.split(":")[1].trim());
            }
            if (line.contains("Computer") && !line.contains("Next Player")) {
                line = reader.readLine();
                if (line.contains("Score")) {
                    String scoreLine = line.split(":")[1].trim();
                    computerTotalScore = Integer.parseInt(scoreLine.split("/")[0].trim());
                    computerRoundScore = Integer.parseInt(scoreLine.split("/")[1].trim());

                }
                line = reader.readLine();
                if (line.contains("Hand")) {
                    buffer = line.split(":")[1].trim();

                    if (buffer.length() > 1) {
                        String[] cards = buffer.split(" ");
                        compHand.addAll(Arrays.asList(cards));
                    }


                }

                line = reader.readLine();
                if (line.contains("Capture Pile")) {
                    buffer = line.split(":")[1].trim();

                    if (buffer.length() > 1) {
                        String[] cards = buffer.split(" ");
                        compCapture.addAll(Arrays.asList(cards));
                    }
                }

                line = reader.readLine();
                if(line.contains("Melds")){
                    compActiveMelds = loadMeldPile(line, mGame.getRound().getTrump(),1);

                }
            }

            if (line.contains("Human") && !line.contains("Next Player")) {
                line = reader.readLine();
                if (line.contains("Score")) {
                    String scoreLine = line.split(":")[1].trim();
                    humanTotalScore = Integer.parseInt(scoreLine.split("/")[0].trim());
                    humanRoundScore = Integer.parseInt(scoreLine.split("/")[1].trim());

                }
                line = reader.readLine();
                if (line.contains("Hand")) {
                    buffer = line.split(":")[1].trim();
                }
                if (buffer.length() > 1) {
                    String[] cards = buffer.split(" ");
                    humanHand.addAll(Arrays.asList(cards));
                }

                line = reader.readLine();
                if (line.contains("Capture Pile")) {
                    buffer = line.split(":")[1].trim();
                }
                if (buffer.length() > 1) {
                    String[] cards = buffer.split(" ");
                    humanCapture.addAll(Arrays.asList(cards));
                }

                line = reader.readLine();
                if(line.contains("Melds")){
                    humanActiveMelds = loadMeldPile(line, mGame.getRound().getTrump(),0);

                }
            }

            if (line.contains("Stock")) {
                buffer = line.split(":")[1].trim();

                if (buffer.length() > 1) {
                    String[] cards = buffer.split(" ");
                    stockPile.addAll(Arrays.asList(cards));
                }
            }

            if (line.contains("Next Player")) {
                if (line.contains("Computer")) {
                    lead_player = "Computer";
                    next_player = "Computer";
                    playButton.setText("Process Computer's Turn");
                    helpButton.setVisibility(View.GONE);

                } else {
                    lead_player = "Human";
                    next_player = "Human";
                    playButton.setText("Play Human's Turn");
                }
                meldButton.setVisibility(View.GONE);
            }

            line = reader.readLine();

        }



        mGame.setRoundNum(roundNum);
        mGame.getRound().getPlayer(0).loadPlayerScore(humanTotalScore, humanRoundScore);
        mGame.getRound().getPlayer(1).loadPlayerScore(computerTotalScore, computerRoundScore);

        mGame.getRound().getPlayer(0).loadPile(humanHand,allCards,"hand");
        allCards = combineAllCards(mGame.getRound().getPlayer(0).getHandPile(), allCards);
        mGame.getRound().getPlayer(1).loadPile(compHand,allCards, "hand");
        allCards = combineAllCards(mGame.getRound().getPlayer(1).getHandPile(), allCards);

        mGame.getRound().getPlayer(0).loadPile(humanCapture,allCards,"capture");
        allCards = combineAllCards(mGame.getRound().getPlayer(0).getCapturePile(), allCards);
        mGame.getRound().getPlayer(1).loadPile(compCapture,allCards ,"capture");
        allCards = combineAllCards(mGame.getRound().getPlayer(1).getCapturePile(), allCards);

        if(humanActiveMelds != null) {
            mGame.getRound().getPlayer(0).loadMelds(humanActiveMelds, allCards);
            for (String each : mGame.getRound().getPlayer(0).getMeldMap().keySet()) {
                allCards.add(String.valueOf(each.charAt(0)) + String.valueOf(each.charAt(1)));
            }
        }

        if(compActiveMelds != null) {
            mGame.getRound().getPlayer(1).loadMelds(compActiveMelds, allCards);
            for (String each : mGame.getRound().getPlayer(1).getMeldMap().keySet()) {
                allCards.add(String.valueOf(each.charAt(0)) + String.valueOf(each.charAt(1)));
            }
        }

        mGame.getRound().stockDeck().loadDeck(stockPile,allCards);
    }

    /**
     * RoundActivity::loadMeldPile
     * Method to load the meld pile
     * @param line String the meld card line in saved file
     * @param trumpCard Card trump card object
     * @param playerIndex int index of the player
     * @return HashMap<String, Vector<Vector<String>>> the map containing all melds and their linked card
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private HashMap<String, Vector<Vector<String>>> loadMeldPile(String line, Card trumpCard, int playerIndex){
        HashMap<String, Vector<Vector<String>>> returnMap = new HashMap<>();
        String buffer =  line.split(":")[1].trim();
        if(!buffer.isEmpty()) {
            String[] melds = buffer.split(",");
            for (String untrimmedMeld : melds) {
                String eachMeld = untrimmedMeld.trim();
                String[] cards_temp = eachMeld.split(" ");
                Vector<String> cards = new Vector<>(Arrays.asList(cards_temp));
                String meldType = mGame.getRound().getPlayer(playerIndex).getMeldType(cards, trumpCard.getSuit().charAt(0));
                if (returnMap.containsKey(meldType)) {
                    Vector<Vector<String>> value = returnMap.get(meldType);
                    value.add(cards);
                    returnMap.put(meldType, value);
                } else {
                    Vector<Vector<String>> value = new Vector<>();
                    value.add(cards);
                    returnMap.put(meldType, value);
                }
            }
        }

        return returnMap;

    }

    /**
     * RoundActivity::combineAllCards
     * Method to combine two vectors together into a single vector
     * @param newCards Vector<Card> list with new cards to be added
     * @param oldCards Vector<Card> list with existing cards
     * @return Vector<String> single list containing the union of the two parameters
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private Vector<String> combineAllCards(Vector<Card> newCards, Vector<String> oldCards){
        for(int i=0;i<newCards.size();i++){
            oldCards.add(newCards.get(i).getFace()+newCards.get(i).getSuit());
        }
        return oldCards;

    }

    /**
     * RoundActivity::endRound
     * Method to end a round
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    private void endRound(){
        screen_log.setText("");
        int humanRoundScore = mGame.getRound().getPlayer(0).getScore();
        int compRoundScore = mGame.getRound().getPlayer(1).getScore();
        mGame.getRound().getPlayer(0).setTotalScore(humanRoundScore);
        mGame.getRound().getPlayer(1).setTotalScore(compRoundScore);
        final int humanTotal = mGame.getRound().getPlayer(0).getTotalScore();
        final int compTotal = mGame.getRound().getPlayer(1).getTotalScore();

        new AlertDialog.Builder(RoundActivity.this)
                .setTitle("End of Round: " +mGame.getRoundNum())
                .setMessage("Round "+ mGame.getRoundNum()+" has ended."+ "\n" +
                            "Round Stats:\n" +
                            "\tHuman Score: "+ humanRoundScore +"\n\tComputer Score: "+compRoundScore+"\n"+
                            "Total Scores:\n"+
                            "\tHuman Total: "+ mGame.getRound().getPlayer(0).getTotalScore()+ "\n"+
                            "\tComputer Total: "+mGame.getRound().getPlayer(1).getTotalScore()+"\n\n"+
                            "Would you like to play another round?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(humanTotal>compTotal) {
                            mGame.setUpNewRound();
                            state = 0;
                            lead_player = "Human";
                            next_player = "Human";
                            gameLogs.add("Human has higher total score and will play first!");
                            getRoundDetails();
                            makeHumanMove();
                        }
                        else if(compTotal>humanTotal){
                            mGame.setUpNewRound();
                            state = 0;
                            lead_player = "Computer";
                            next_player = "Computer";
                            gameLogs.add("Computer has higher total score and will play first!");
                            getRoundDetails();
                            makeComputerMove();
                        }
                        else{
                            gameLogs.add("Total Scores are tie! A coin toss will decide the next player!");
                            setUpNewRound();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        displayGameWinner();
                    }
                })
                .show();
    }

    /**
     * RoundActivity::displayGameWinner
     * Method to display the game winner and end the game
     * @author Nitesh Parajuli
     * @date 12/08/20.
     */
    public void displayGameWinner(){
        int humanScore = mGame.getRound().getPlayer(0).getTotalScore();
        int compScore = mGame.getRound().getPlayer(1).getTotalScore();
        String message="";
        if(humanScore>compScore){
            message+= "Human won the game!\nHuman Score: "+humanScore+"\nComputer Score: "+compScore;
        }
        else if(compScore>humanScore) {
            message+= "Computer won the game!\nComputer Score: "+compScore+"\nHuman Score: "+humanScore;
        }
        else{
            message+="It's a tie! Both players have the same total score.\nComputer Score: "+compScore+"\nHuman Score: "+humanScore;
        }
        message+="\n\n Thank you for playing Pinochle!";

        new AlertDialog.Builder(RoundActivity.this)
                .setTitle("End of Game.")
                .setMessage(message)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                })
                .show();

    }
}