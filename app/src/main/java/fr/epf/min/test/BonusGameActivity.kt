package fr.epf.min.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

class BonusGameActivity : AppCompatActivity() {
    private lateinit var listeAllPays: List<Pays>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu_bonus)

    }
}