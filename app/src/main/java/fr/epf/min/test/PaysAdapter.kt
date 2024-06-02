package fr.epf.min.test

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PaysViewHolder(view : View) : RecyclerView.ViewHolder(view)

class PaysAdapter (val pays : List<Pays>): RecyclerView.Adapter<PaysViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_short_pays, parent, false)
        return PaysViewHolder(view)    }

    override fun getItemCount() = pays.size

    override fun onBindViewHolder(holder: PaysViewHolder, position: Int) {
        val unPays = pays[position]
        Log.d(TAG, unPays.toString())
        val view = holder.itemView

        val paysNameTextView = view.findViewById<TextView>(R.id.shortdetailspays_paysname_text)
        paysNameTextView.text= unPays.commonName
        val imageView = view.findViewById<ImageView>(R.id.shortdetailpays_flag_ImageView)

        Glide.with(view).load(unPays.flagUrl).into(imageView)

        val cardVIew = view.findViewById<CardView>(R.id.shortdetailspays_cardView)
        cardVIew.setOnClickListener() {
            with(it.context){
                val intent = Intent(this, DetailsPaysActivity::class.java)
                intent.putExtra(PAYS_ID_EXTRA, unPays)
                startActivity(intent)
            }
        }
    }

}