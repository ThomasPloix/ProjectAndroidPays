package fr.epf.min.test


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu

import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val TAG = "TEST_PAYS"

class DetailsPaysActivity : AppCompatActivity () {

        lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pays)

        recyclerView = findViewById<RecyclerView>(R.id.list_pays_recyclerview)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val pays = Pays.generate(1)
        recyclerView.adapter =PaysAdapter(pays)
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
                synchro()
            }
            R.id.action_recherche_france ->{
                Log.d("TEST", "Testing France")
                val editText = EditText(this).apply {
                    hint = "Entrez le nom ici"
                }

                AlertDialog.Builder(this).apply {
                    setTitle("Recherchez un pays")
                    setView(editText)
                    setPositiveButton("OK") { _, _ ->
                        val userInput = editText.text.toString()
                        rechercheFrance(userInput)
                    }
                    setNegativeButton("Annuler", null)
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun rechercheFrance(pays: String) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val paysService =
            retrofit.create(PaysService::class.java)
        runBlocking {
            try {
                val pays = paysService.getUnPays(pays)
                Log.d(TAG, "synchro: ${pays}")
                Affichage(pays)
            }catch (e: Exception){
                Log.e(TAG,"Erreur lors de la requête API : ${e.message}")
            }
        }
    }

    private fun synchro() {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
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
        runBlocking {
            try {
                val pays = paysService.getAllPays()
                //val pays = paysService.getUnPays("france")
                //val pays = paysService.getPaysBySubregion("Western Europe")
                Log.d(TAG, "synchro: ${pays}")
                Affichage(pays)
            }catch (e: Exception){
                Log.e(TAG,"Erreur lors de la requête API : ${e.message}")
            }
        }
    }


    private fun Affichage(countryS : List<Country>){
        val paysAffiche = countryS.map {
            try {
                Pays(
                    it.name.common,
                    it.name.official,
                    it.region,
                    it.capital[0],
                    it.currencies.toString(),
                    it.population,
                    it.flags.png
                )
            }catch (e: Exception){
                Log.e(TAG, "Erreur lors de la transformation de l'objet Pays: ${e.message} + $it")
                Pays(
                    it.name.common ?: "Nom inconnu",
                    it.name.official ?: "Nom officiel inconnu",
                    it.region ?: "Région inconnue",
                    if (it.capital.isNotEmpty()) it.capital[0] else "Capitale inconnue",
                    it.currencies?.toString() ?: "Monnaies inconnues",
                    it.population ?: 0,
                    it.flags?.png ?: ""
                )
            }
        }
        val adapter = PaysAdapter(paysAffiche)

        recyclerView.adapter = adapter

    }

}