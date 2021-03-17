package com.m1

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : Activity() {
    var mm: minesw? = null
    var bx: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mm = findViewById(R.id.mmx)
        bx = findViewById(R.id.button)
        bx?.setOnClickListener(View.OnClickListener { mm?.isR = 1 })
    }

    // This method executes when the player starts the game
    override fun onResume() {
        super.onResume()
        minesw.SS++

        // Tell the pongView resume method to execute
        mm!!.resume()
    }

    // This method executes when the player quits the game
    override fun onPause() {
        super.onPause()

        // Tell the pongView pause method to execute
        mm!!.pause()
    }
}