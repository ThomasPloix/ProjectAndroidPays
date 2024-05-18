package fr.epf.min.test


import android.os.Bundle
import android.util.Log
import android.view.Menu

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
        }
        return super.onOptionsItemSelected(item)
    }
    private fun synchro() {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
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
            //val pays = paysService.getUnPays("france")
            val pays = paysService.getPaysBySubregion("Western Europe")
            //val pays = paysService.getAllPays()
            Log.d(TAG, "synchro: ${pays}")

            val unPays = pays.map {
                Pays(it.name.common,
                    it.name.official,
                    it.region,
                    it.capital[0],
                    it.currencies.toString(),
                    it.population,
                    it.flags.png
                )
            }

            val adapter = PaysAdapter(unPays)

           recyclerView.adapter = adapter

        }



    }
}