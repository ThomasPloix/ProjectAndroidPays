package fr.epf.min.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BonusGameActivity : AppCompatActivity() {
    private lateinit var listeAllPays: List<Pays>
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu_bonus)
        recyclerView = findViewById<RecyclerView>(R.id.jeu_bonus_recyclerview)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val pays = Pays.generate(1)
        recyclerView.adapter =JeuBonusAdapter(pays)
    }
}