package fr.epf.min.test

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
        val view = layoutInflater.inflate(R.layout.activity_details_pays, parent, false)
        return PaysViewHolder(view)    }

    override fun getItemCount() = pays.size

    override fun onBindViewHolder(holder: PaysViewHolder, position: Int) {
        val unPays = pays[position]
        Log.d(TAG, unPays.toString())
        val view = holder.itemView

        val paysNameTextView = view.findViewById<TextView>(R.id.view_pays_name_text)
        paysNameTextView.text = unPays.commonName

        val paysCapitalTextView = view.findViewById<TextView>(R.id.view_pays_capital_text)
        paysCapitalTextView.text = unPays.capital

        val paysRegionTextView = view.findViewById<TextView>(R.id.view_pays_region_text)
        paysRegionTextView.text = unPays.region

        val paysCurrencyTextView = view.findViewById<TextView>(R.id.view_pays_currency_text)
        paysCurrencyTextView.text = unPays.currency



        val imageView = view.findViewById<ImageView>(R.id.view_pays_flag_image)
//        imageView.setImageResource(
//            if(client.gender == Gender.MAN) R.drawable.man else R.drawable.woman
//        )

        Glide.with(view).load(unPays.flagUrl).into(imageView)

        val cardVIew = view.findViewById<CardView>(R.id.pays_view_cardview)
//        cardVIew.click {
//            with(it.context){
//                val intent = Intent(this, DetailsClientActivity::class.java)
//                intent.putExtra(CLIENT_ID_EXTRA, client)
//                startActivity(intent)
//            }
//        }
    }

}