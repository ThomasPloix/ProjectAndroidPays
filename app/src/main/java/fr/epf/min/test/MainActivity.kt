package fr.epf.min.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val detailsButon = findViewById<Button>(R.id.home_detailspays_button)

        detailsButon.setOnClickListener(){
            val intent = Intent(this, DetailsPaysActivity::class.java)
            Log.d("TEST","Click")

            startActivity(intent)
        }

    }

}