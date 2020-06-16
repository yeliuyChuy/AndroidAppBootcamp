package com.example.android.a02connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 1; // 1 = red, -1 = yellow
    String winner = "";
    int[] counterState = {
             0, 0, 0,
             0, 0, 0,
             0, 0, 0,
    };
    boolean gameOver = false;

    public  boolean isFull(int[] counterState){
        for(int counter:counterState){
            if(counter == 0){return false;}
        }
        return true;
    }

    public boolean isWinning(int[] counterState){
        //check horizontally
        for(int row = 0; row < 3; row ++){
            if(Math.abs(counterState[3*row] + counterState[3*row+1] + counterState[3*row+2]) == 3){
                return true;
            }
        }
        //check vertically
        for(int col = 0; col < 3; col++){
            if(Math.abs(counterState[col] + counterState[col+3] + counterState[col+6]) == 3){
                return true;
            }
        }
        //check diagonally
        if(Math.abs(counterState[0] + counterState[4] + counterState[8]) == 3){return true;}
        if(Math.abs(counterState[2] + counterState[4] + counterState[6]) == 3){return true;}
        return false;
    }

    public void initiate(View view){

        activePlayer = 1; // 1 = red, -1 = yellow
        winner = "";
        gameOver = false;

        Button restart = (Button)findViewById(R.id.playAgain);
        TextView text = (TextView)findViewById(R.id.textView);
        androidx.gridlayout.widget.GridLayout grid = findViewById(R.id.gridLayout);

        for(int i = 0; i < grid.getChildCount(); i++){
            ImageView counter = (ImageView)grid.getChildAt(i);
            counter.setImageDrawable(null);

            counterState[i] = 0;
        }
        text.setVisibility(View.INVISIBLE);
        restart.setVisibility(View.INVISIBLE);
    }

    public void dropIn(View view){

        if(!gameOver) {
            ImageView counter = (ImageView) view;
            Log.i("tag",counter.getTag().toString());
            counter.setTranslationY(-1500);

            //Update the state of counter array.
            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            counterState[tappedCounter] = activePlayer;

            //Play the animation
            if (activePlayer == -1) {
                activePlayer = 1;
                counter.setImageResource(R.drawable.red);
            } else {
                activePlayer = -1;
                counter.setImageResource(R.drawable.yellow);
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            //Check game state
            if (isWinning(counterState)||isFull(counterState)) {
                //Pause the game
                gameOver = true;
                //display button to replay
                Button restart = (Button)findViewById(R.id.playAgain);
                TextView text = (TextView)findViewById(R.id.textView);

                text.setVisibility(View.VISIBLE);
                restart.setVisibility(View.VISIBLE);

                if(isWinning(counterState)){
                    Log.i("Info", Integer.toString(activePlayer) + " Wins!");
                    if(activePlayer == 1){
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }
                    Toast.makeText(this, winner + " Wins!", Toast.LENGTH_SHORT).show();
                    text.setText(winner + " Wins!");
                } else{
                    Toast.makeText(this, " Ties!", Toast.LENGTH_SHORT).show();
                    text.setText("Ties!!");
                }
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
