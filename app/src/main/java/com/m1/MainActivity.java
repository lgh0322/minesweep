package com.m1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public minesw mm;
    public Button bx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mm=findViewById(R.id.mmx);
        bx=findViewById(R.id.button);
        bx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mm.isR=1;
            }
        });
    }


    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();
        mm.SS++;

        // Tell the pongView resume method to execute
       mm.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the pongView pause method to execute
        mm.pause();
    }


}
