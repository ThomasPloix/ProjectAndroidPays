package fr.epf.min.test

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class JeuBonusAdapter (val pays : List<Pays>): RecyclerView.Adapter<PaysViewHolder>(){

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
    }
}