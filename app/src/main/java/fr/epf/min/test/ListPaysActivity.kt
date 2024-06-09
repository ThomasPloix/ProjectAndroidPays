package fr.epf.min.test




import android.os.Bundle
import android.util.Log
import android.view.Menu

import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val TAG = "TEST_PAYS"

class ListPaysActivity : AppCompatActivity () {

        private lateinit var recyclerView: RecyclerView
        private lateinit var listeAllPays: List<Pays>
        private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pays)

        recyclerView = findViewById<RecyclerView>(R.id.list_pays_recyclerview)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        lifecycleScope.launch {
            DataStoreRepo.read(this@ListPaysActivity)
            Log.d("TEST",DataStoreRepo.FavoriteCountries.toString())
        }

        val pays = DataStoreRepo.FavoriteCountries

        recyclerView.adapter =PaysAdapter(pays)
        searchView = findViewById(R.id.search)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    rechercheFrance(newText)
                } else {
                    rechercheFrance("")
                }
                return false
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("TEST","Creat")
        menuInflater.inflate(R.menu.detailspays,menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sendinfo ->{
                Log.d("TEST", "testing")
                runBlocking { synchro() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun rechercheFrance(paysNom: String) {

        runBlocking {
            try {
                val paysFiltre = listeAllPays.filter { unPays -> unPays.commonName.contains(paysNom, ignoreCase = true)
                        || unPays.capital.contains(paysNom, ignoreCase = true) }
                Log.d(TAG, "synchro: ${paysFiltre}")
                val adapter = PaysAdapter(paysFiltre)
                recyclerView.adapter = adapter
            }catch (e: Exception){
                Log.e(TAG,"Erreur lors de la requête API : ${e.message}")
            }
        }
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

//
//

        try {
            val pays = paysService.getAllPays()

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
                    it.flags.png,
                    false
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
                    it.flags?.png ?: "",
                    false
                )
            }
        }
        val adapter = PaysAdapter(listeAllPays)

        recyclerView.adapter = adapter

    }

}