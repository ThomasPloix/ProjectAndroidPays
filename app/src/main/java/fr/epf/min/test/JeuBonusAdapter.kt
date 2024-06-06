package fr.epf.min.test

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JeuBonusAdapter (private val pays : List<Pays>): RecyclerView.Adapter<PaysViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_jeu_bonus_reponses, parent, false)
        return PaysViewHolder(view)    }

    override fun getItemCount(): Int {
        return pays.size
    }

    override fun onBindViewHolder(holder: PaysViewHolder, position: Int) {
        val unPays = pays[position]
        Log.d(TAG, unPays.toString())
        val view = holder.itemView

        val paysNameTextView = view.findViewById<TextView>(R.id.jeu_bonus_nom_pays)
        paysNameTextView.text= unPays.commonName
        val paysRegionTextView = view.findViewById<TextView>(R.id.jeu_bonus_region)
        paysRegionTextView.text= unPays.region
        val paysSousRegionTextView = view.findViewById<TextView>(R.id.jeu_bonus_sous_region)
        paysSousRegionTextView.text= unPays.subregion
        val paysCapitaleTextView = view.findViewById<TextView>(R.id.jeu_bonus_capitale)
        paysCapitaleTextView.text= unPays.capital
        val paysMonnaieTextView = view.findViewById<TextView>(R.id.jeu_bonus_monnaie)
        paysMonnaieTextView.text= unPays.currency
        val paysLanguesTextView = view.findViewById<TextView>(R.id.jeu_bonus_langages)
        paysLanguesTextView.text = unPays.language
        val paysPopulationTextView = view.findViewById<TextView>(R.id.jeu_bonus_population)
        paysPopulationTextView.text = unPays.population.toString()
        val paysSurfaceTextView = view.findViewById<TextView>(R.id.jeu_bonus_area)
        paysSurfaceTextView.text = unPays.area.toString()


    }
}