package fr.epf.min.test

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class JeuBonusAdapter (private val pays : List<Pays>, private val paysatrouver : Pays): RecyclerView.Adapter<PaysViewHolder>(){

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
        paysMonnaieTextView.text= unPays.currency.replace("{", "").replace("}", "")
        val paysLanguesTextView = view.findViewById<TextView>(R.id.jeu_bonus_langages)
        paysLanguesTextView.text = unPays.language.replace("{", "").replace("}", "")
        val paysPopulationTextView = view.findViewById<TextView>(R.id.jeu_bonus_population)
        paysPopulationTextView.text = unPays.population.toString()
        val paysSurfaceTextView = view.findViewById<TextView>(R.id.jeu_bonus_area)
        paysSurfaceTextView.text = unPays.area.toString()

        if (unPays.commonName==paysatrouver.commonName) {
            paysNameTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else if (unPays.commonName[0]==paysatrouver.commonName[0]){
            paysNameTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_orange)
        }
        if (unPays.region==paysatrouver.region) {
            paysRegionTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else{
            paysRegionTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_red)
        }
        if (unPays.subregion==paysatrouver.subregion) {
            paysSousRegionTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else{
            paysSousRegionTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_red)
        }
        if (unPays.capital==paysatrouver.capital) {
            paysCapitaleTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else if (unPays.capital[0]==paysatrouver.capital[0]) {
            paysCapitaleTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_orange)
        }
        else{
            paysCapitaleTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_red)
        }
        if (unPays.currency==paysatrouver.currency) {
            paysMonnaieTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else if (paysatrouver.currency.contains(unPays.currency.replace("{", "").replace("}", ""))) {
            paysMonnaieTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_orange)
        }
        else{
            paysMonnaieTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_red)
        }
        if (unPays.language==paysatrouver.language) {
            paysLanguesTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else if (paysatrouver.language.contains(unPays.language.replace("{", "").replace("}", ""))) {
            paysLanguesTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_orange)
        }
        else{
            paysLanguesTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_red)
        }
        if (unPays.area == paysatrouver.area) {
            paysSurfaceTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else if (unPays.area<paysatrouver.area) {
            paysSurfaceTextView.background = view.resources.getDrawable(R.drawable.baseline_arrow_upward_24)
        }
        else{
            paysSurfaceTextView.background = view.resources.getDrawable(R.drawable.baseline_arrow_downward_24)
        }
        if (unPays.population == paysatrouver.population) {
            paysPopulationTextView.background = view.resources.getDrawable(R.drawable.baseline_square_24_green)
        }
        else if (unPays.population<paysatrouver.population) {
            paysPopulationTextView.background = view.resources.getDrawable(R.drawable.baseline_arrow_upward_24)
        }
        else{
            paysPopulationTextView.background = view.resources.getDrawable(R.drawable.baseline_arrow_downward_24)
        }

    }
}