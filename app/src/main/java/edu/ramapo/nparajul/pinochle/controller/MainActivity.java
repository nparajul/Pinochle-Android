/*
 ************************************************************
 * Name:  Nitesh Parajuli                                  *
 * Project:  Project 3 Pinochle Java/Android			   *
 * Class:  CMPS 366 OPL				                       *
 * Date:  12/8/2020				                           *
 ************************************************************
*/
package edu.ramapo.nparajul.pinochle.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.ramapo.nparajul.pinochle.R;

public class MainActivity extends AppCompatActivity {

    /**
     * RoundActivity::onCreate
     * loads the activity and sets the content view
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * RoundActivity::startGame
     * Start game with a new round
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    public void startGame(View view) {

        Intent intent = new Intent(this,RoundActivity.class);
        intent.putExtra("newRound",true);
        startActivity(intent);
        finish();

    }

    /**
     * RoundActivity::startGame
     * Start game with a saved round
     * @author Nitesh Parajuli
     * @date 12/08/20
     */
    public void loadGame(View view){

        AlertDialog.Builder load_file = new AlertDialog.Builder(MainActivity.this);
        load_file.setTitle("LOAD A GAME");
        final String check = Environment.getDataDirectory().getAbsolutePath();
        final String relative= "/data/" + getPackageName() +"/files";

        final File[] all_files = new File(check+relative).listFiles();
        List<String> text_files = new ArrayList<String>();

        for(File onefile: all_files){
            String file_name = onefile.getName();
            if (file_name.endsWith(".txt")){
                text_files.add(file_name);
            }
        }

        int size = text_files.size();
        String[] options = new String[size];
        options = text_files.toArray(options);

        load_file.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView options = ((AlertDialog)dialog).getListView();
                String selected_file = (String) options.getAdapter().getItem(which);

                String filePath = check+relative +"/"+selected_file;

                Intent intent = new Intent(MainActivity.this, RoundActivity.class);
                intent.putExtra("newRound", false);
                intent.putExtra("file_name", filePath);
                startActivity(intent);
                finish();

            }
        });

        load_file.show();
    }
}