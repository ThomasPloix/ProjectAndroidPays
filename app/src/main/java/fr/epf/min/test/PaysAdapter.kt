package fr.epf.min.test

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val PAYS_ID_EXTRA = "PaysId"

class PaysViewHolder(view : View) : RecyclerView.ViewHolder(view)

class PaysAdapter (private val pays : List<Pays>): RecyclerView.Adapter<PaysViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_short_pays, parent, false)
        return PaysViewHolder(view)    }

    override fun getItemCount() = pays.size

    override fun onBindViewHolder(holder: PaysViewHolder, position: Int) {
        val unPays = pays[position]
        val view = holder.itemView

        val paysNameTextView = view.findViewById<TextView>(R.id.shortdetailspays_paysname_text)
        paysNameTextView.text= unPays.commonName
        val paysCapitaleTextView = view.findViewById<TextView>(R.id.shortdetailspays_payscapitale_text)
        paysCapitaleTextView.text= unPays.capital
        val imageView = view.findViewById<ImageView>(R.id.shortdetailpays_flag_ImageView)

        Glide.with(view).load(unPays.flagUrl).into(imageView)

        val checkBox = view.findViewById<CheckBox>(R.id.shortdetailspays_favory_checkbox)
        checkBox.isChecked = unPays.favori

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                unPays.favori=true
                runBlocking{
                    launch {
                        DataStoreRepo.FavoriteCountries.add(unPays)
                        DataStoreRepo.save(it.context) }
                    }


            } else {
                unPays.favori=false
                runBlocking {
                    launch {
                        DataStoreRepo.FavoriteCountries.remove(unPays)
                        DataStoreRepo.save(it.context)
                    }
                }
            }
        }

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