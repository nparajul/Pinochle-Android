/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
 */
package edu.ramapo.nparajul.pinochle.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.ArrayList;
import java.util.Vector;
import edu.ramapo.nparajul.pinochle.R;

public class PlayDialog extends AppCompatDialogFragment implements View.OnClickListener {

    // *********************************************************
    // ******************** Class Variables ********************
    // *********************************************************

    //display playable cards
    private LinearLayout linearLayout;

    //imageview to show cards
    private ImageView imageview;

    //array to store cards selected by the user
    ArrayList<String> selectedCards = new ArrayList<>();

    //listener object for play dialog
    private PlayDialogListener listener;

    //state of the game (for meld or turn rules)
    int state;

    //vector holding all cards to display
    Vector<String> playableCards = new Vector<>();

    /**
     * PlayDialog::onCreateDialog
     * loads the dialog with cards and sets onClick listeners for all the buttons in the dialog
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.play_screen, null);
        linearLayout = view.findViewById(R.id.playable_card_linearlayout);
        Bundle mArgs = getArguments();
        ArrayList<String> argCards = mArgs.getStringArrayList("cards");
        playableCards = new Vector<String>(argCards);
        state = mArgs.getInt("state");
        loadViews(playableCards);

        if(state == 0) {
            builder.setView(view).setTitle(mArgs.getString("player") + "'s Turn")
                    .setMessage("Please select a card from below: ")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Make a move", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.humanSelectedCards(selectedCards);
                        }
                    });
        }
        else
        {
            builder.setView(view).setTitle(mArgs.getString("player") + "'s Meld")
                    .setMessage("Please select your meld from below: ")
                    .setNegativeButton("No Melds to Declare", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.noMeld();
                        }
                    })
                    .setPositiveButton("Declare a Meld", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.humanSelectedCards(selectedCards);
                        }
                    });
        }



        return builder.create();


    }

    /**
     * PlayDialog::onAttach
     * sets the listener for the dialog
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PlayDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ " must implement PlayDialogListener");
        }
    }

    /**
     * PlayDialog::loadViews
     * loads the layout with card images
     * @param playableCards Vector<String> list of all playable cards to load
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    public void loadViews(Vector<String> playableCards){
        for(String card:playableCards)
        {
            imageview = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150,190);
            layoutParams.setMargins(10,10,10,10);
            imageview.setLayoutParams(layoutParams);

            Context context = linearLayout.getContext();
            int id = context.getResources().getIdentifier(card, "drawable", context.getPackageName());
            imageview.setImageResource(id);

            imageview.setTag(card);
            imageview.setPadding(5,5,5,5);
            imageview.setId(id);

            imageview.setClickable(true);
            imageview.setOnClickListener((View.OnClickListener) this);

            linearLayout.addView(imageview);
        }
    }

    /**
     * PlayDialog::onClick
     * onclick listeners for the card images.
     * adds cards to a list of selected card
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    @Override
    public void onClick(View view){

        if(state == 1) {
            if (view.getBackground() == null) {

                Drawable selectedCard = getResources().getDrawable(R.drawable.selectedcard);
                view.setBackground(selectedCard);
                String card_selected = (String) view.getTag();
                selectedCards.add(card_selected);

            } else {
                String card_selected = (String) view.getTag();
                selectedCards.remove(card_selected);
                view.setBackground(null);
            }
        }
        else{

            if (view.getBackground() == null) {
                if(selectedCards.size()>0) {
                    View view1 = linearLayout.findViewWithTag(selectedCards.get(0));
                    view1.setBackground(null);
                    selectedCards.clear();
                }

                Drawable selectedCard = getResources().getDrawable(R.drawable.selectedcard);
                view.setBackground(selectedCard);
                String card_selected = (String) view.getTag();
                selectedCards.add(card_selected);

            } else {
                view.setBackground(null);
                selectedCards.clear();
            }
        }
    }

    public interface PlayDialogListener{
        void humanSelectedCards(ArrayList<String> selectedCards);
        void noMeld();
    }
}
