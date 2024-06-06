package fr.epf.min.test

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class JeuBonusActivity : AppCompatActivity() {
    private lateinit var listeAllPays: List<Pays>
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu_bonus)
        recyclerView = findViewById<RecyclerView>(R.id.jeu_bonus_recyclerview)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val pays = Pays.generate(10)
        recyclerView.adapter =JeuBonusAdapter(pays)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detailspays,menu)
        super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sendinfo -> {
                Log.d("TEST", "testing")
                runBlocking { synchro() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun synchro() {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val paysService =
            retrofit.create(PaysService::class.java)

//        CoroutineScope(Dispatchers.IO).launch {
//            val users = userService.getUsers(15)
//        }
//

        try {
            var pays = paysService.getAllPays()
            //val pays = paysService.getUnPays("france")
            //val pays = paysService.getPaysBySubregion("Western Europe")
            Log.d(TAG, "synchro: ${pays}")
            Affichage(pays)
        }catch (e: Exception){
            Log.e(TAG,"Erreur lors de la requête API : ${e.message}")
        }
    }


    private fun Affichage(countrys : List<Country>){
        listeAllPays = countrys.map {
            try {
                Pays(
                    it.name.common,
                    it.name.official,
                    it.region,
                    it.subregion,
                    it.languages.toString(),
                    it.capital[0],
                    it.currencies.map{ e-> e.key to e.value.toString() }.toMap().toString(),
                    it.population,
                    it.area,
                    it.flags.png
                )
            }catch (e: Exception){
                Log.e(TAG, "Erreur lors de la transformation de l'objet Pays: ${e.message} + $it")
                Pays(
                    it.name.common ?: "Nom inconnu",
                    it.name.official ?: "Nom officiel inconnu",
                    it.region ?: "Région inconnue",
                    it.subregion ?:"Sous region inconnue",
                    it.languages.toString() ?:"Pb langue",
                    if (it.capital.isNotEmpty()) it.capital[0] else "Capitale inconnue",
                    it.currencies?.toString() ?: "Monnaies inconnues",
                    it.population ?: 0,
                    it.area ?: 0.0,
                    it.flags?.png ?: ""
                )
            }
        }
        val ptiteliste : List<Pays> = listeAllPays.filter{ unPays -> unPays.commonName.contains("bel", ignoreCase = true) }
        val adapter = JeuBonusAdapter(ptiteliste)

        recyclerView.adapter = adapter

    }
}