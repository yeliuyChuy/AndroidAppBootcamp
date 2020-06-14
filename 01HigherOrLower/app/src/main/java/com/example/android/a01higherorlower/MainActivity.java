package com.example.android.a01higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int numRand;

    public void guess(View view){

        String toastMessage;
        EditText editText1 = (EditText)findViewById(R.id.editText1);
        int guessedValue = Integer.parseInt(editText1.getText().toString());

        if(guessedValue > numRand){
            toastMessage = "Lower!";
        }else if(guessedValue < numRand){
            toastMessage = "Higher!";
        }else {
            toastMessage = "You got it!";
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        Log.i("Random Number", Integer.toString(numRand));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rand = new Random();

        numRand = rand.nextInt(20) + 1;
    }
}
