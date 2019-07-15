package com.mr.puzzle3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int BUTTON_REQUEST = 1;

    private Button btnNewAlertDialogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        Button buttonPlay = findViewById(R.id.play);
        Button buttonResults = findViewById(R.id.results);
        Button buttonClose = findViewById(R.id.close);

//play
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.layout_level, null);

                Button buttonEasyChoice = (Button) mView.findViewById(R.id.easyChoice);
                Button buttonMediumChoice = (Button) mView.findViewById(R.id.mediumChoice);
                Button buttonHardChoice = (Button) mView.findViewById(R.id.hardChoice);
                Button buttonExtremeChoice = (Button) mView.findViewById(R.id.extremeChoice);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                buttonEasyChoice.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intentPlay = new Intent(getApplicationContext(), AssemblePuzzle.class);
                        intentPlay.putExtra("dimensions", 3);
                        setResult(RESULT_OK, intentPlay);
                        startActivityForResult(intentPlay, BUTTON_REQUEST);
                        dialog.dismiss();
                    }
                });

                buttonMediumChoice.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intentPlay = new Intent(getApplicationContext(), AssemblePuzzle.class);
                        intentPlay.putExtra("dimensions", 4);
                        setResult(RESULT_OK, intentPlay);
                        startActivityForResult(intentPlay, BUTTON_REQUEST);
                        dialog.dismiss();
                    }
                });

                buttonHardChoice.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intentPlay = new Intent(getApplicationContext(), AssemblePuzzle.class);
                        intentPlay.putExtra("dimensions", 5);
                        setResult(RESULT_OK, intentPlay);
                        startActivityForResult(intentPlay, BUTTON_REQUEST);
                        dialog.dismiss();
                    }
                });

                buttonExtremeChoice.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intentPlay = new Intent(getApplicationContext(), AssemblePuzzle.class);
                        intentPlay.putExtra("dimensions", 6);
                        setResult(RESULT_OK, intentPlay);
                        startActivityForResult(intentPlay, BUTTON_REQUEST);
                        dialog.dismiss();
                    }
                });

                //Intent intentPlay = new Intent(getApplicationContext(), AssemblePuzzle.class);
                //intentPlay.putExtra("dimensions", 5);
                //setResult(RESULT_OK, intentPlay);
                //startActivityForResult(intentPlay, BUTTON_REQUEST);
            }
        });



//results
        buttonResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageChoosing.class);
                startActivity(intent);
            }
        });

//close
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                // View mView = getLayoutInflater().inflate(R.layout.layout_close, null);
                mBuilder.setMessage("Are You sure???")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                mBuilder.show();
            }
        });
    }

 /*   @Override
    public void EasyChoice(DialogFragment dialog) {

    }

    @Override
    public void MediumChoice(DialogFragment dialog) {

    }

    @Override
    public void HardChoice(DialogFragment dialog) {

    }

    @Override
    public void ExtremeChoice(DialogFragment dialog) {

    }*/


}
