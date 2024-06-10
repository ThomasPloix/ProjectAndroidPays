package fr.epf.min.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button


import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity () {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val detailsButton = findViewById<Button>(R.id.home_detailspays_button)
        val jeubonusButton = findViewById<Button>(R.id.home_jeubonus_button)

        detailsButton.setOnClickListener(){
            val intent = Intent(this, ListPaysActivity::class.java)
            startActivity(intent)
        }
        jeubonusButton.setOnClickListener(){
            val intent = Intent(this, JeuBonusActivity::class.java)
            startActivity(intent)
        }


    }


}
