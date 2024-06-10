package fr.epf.min.test

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailsPaysActivity: AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_pays)

        val paysNameTextView = findViewById<TextView>(R.id.view_pays_name_text)
        val paysCapitalTextView = findViewById<TextView>(R.id.view_pays_capital_text)
        val paysRegionTextView = findViewById<TextView>(R.id.view_pays_region_text)
        val paysCurrencyTextView = findViewById<TextView>(R.id.view_pays_currency_text)
        val paysLanguesTextView = findViewById<TextView>(R.id.view_pays_langues_text)
        val paysPopulationTextView = findViewById<TextView>(R.id.view_pays_population_text)
        val paysSurfaceTextView = findViewById<TextView>(R.id.view_pays_surface_text)


        val imageView = findViewById<ImageView>(R.id.view_pays_flag_image)



        intent.extras?.apply {
            val unPays = getParcelable(PAYS_ID_EXTRA) as? Pays

            unPays?.let {
                val patternCurrency = Regex("""name=([^,]*)""")

                val currencyName = patternCurrency.find(unPays.currency)?.let {
                    it.groupValues[1]
                } ?: "Euroo"

                val patternLangues = Regex("""=(.*?)(,|\})""")
                var languesName: String = "Français"
                patternLangues.findAll(unPays.language).let { matches ->
                    languesName = matches.map{ it.groupValues[1] }.joinToString(", ")
                }
                val paysPop = String.format("%.2f",unPays.population.toDouble()/1000000)

                paysNameTextView.text = unPays.commonName

                paysCapitalTextView.text = unPays.capital

                paysRegionTextView.text = unPays.region

                paysCurrencyTextView.text = currencyName

                paysLanguesTextView.text = languesName

                paysPopulationTextView.text = "${paysPop} M"

                paysSurfaceTextView.text = "${unPays.area.toInt()} km²"

                Glide.with(this@DetailsPaysActivity).load(unPays.flagUrl).into(imageView)
            }
        }
    }
}