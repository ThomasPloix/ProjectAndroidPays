package fr.epf.min.test

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class JeuBonusActivity : AppCompatActivity() {
    private lateinit var listeAllPays: List<Pays>
    private lateinit var listePaysGuess: ArrayList<Pays>
    private lateinit var recyclerView: RecyclerView
    private lateinit var listView: ListView
    private lateinit var searchView: SearchView
    private lateinit var paysatrouver: Pays
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu_bonus)
        val paysGen = Pays.generate(2)
        val paysNom = paysGen.map { it.commonName }
        var nbessais = 0
        paysatrouver = paysGen[0]

        recyclerView = findViewById<RecyclerView>(R.id.jeu_bonus_recyclerview)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter =JeuBonusAdapter(paysGen, paysatrouver)

        searchView = findViewById(R.id.jeu_bonus_searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.setQuery("", false)
                searchView.clearFocus()
                if (listView.getItemAtPosition(0) == "Nom1") {
                    return true
                }
                val nomPaysSelected = listView.getItemAtPosition(0) as String
                val paysSelected =
                    listeAllPays.filter { unPays -> unPays.commonName == nomPaysSelected }
                listeAllPays =
                    listeAllPays.filter { unPays -> unPays.commonName != nomPaysSelected }
                if (paysSelected.isNotEmpty()) {
                    listePaysGuess.add(0, paysSelected[0])
                    nbessais++
                    submitPays(paysSelected[0], nbessais)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 1) {
                    recherchePays(newText)
                    listView.visibility = ListView.VISIBLE
                } else {
                    listView.visibility = ListView.GONE
                }
                return false
            }
        })

        listView = findViewById<ListView>(R.id.jeu_bonus_listView)
        listePaysGuess = ArrayList<Pays>()
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            searchView.setQuery("", false)
            searchView.clearFocus()
            if (listView.getItemAtPosition(0) == "Nom1") {
                Log.d("TEST", "Erreur de synchro")
            }
            else {
                val nomPaysSelected = parent.getItemAtPosition(position) as String
                val paysSelected = listeAllPays.filter { unPays -> unPays.commonName == nomPaysSelected }
                listeAllPays = listeAllPays.filter { unPays -> unPays.commonName != nomPaysSelected }
                if (paysSelected.isNotEmpty()) {
                    listePaysGuess.add(0, paysSelected[0])
                    nbessais++
                    submitPays(paysSelected[0], nbessais)
                }
            }
        }
        listView.adapter =  ArrayAdapter<String>(this, R.layout.activity_jeu_bonus_listview_item, paysNom)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detailspays,menu)
        super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sendinfo -> {
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
            val pays = paysService.getAllPays()
            //val pays = paysService.getUnPays("france")
            //val pays = paysService.getPaysBySubregion("Western Europe")
            Log.d(TAG, "synchro: ${pays}")
            MappingCountryPays(pays)
        }catch (e: Exception){
            Log.e(TAG,"Erreur lors de la requête API : ${e.message}")
        }
    }


    private fun MappingCountryPays(countrys : List<Country>){
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
        randomPays(listeAllPays)
        recyclerView.adapter =JeuBonusAdapter(listePaysGuess, paysatrouver)
    }

    private fun randomPays(ptiteliste: List<Pays>) {
        val random = (0..ptiteliste.size-1).random()
        paysatrouver = ptiteliste[random]
        Log.d(TAG, "randomPays: $paysatrouver")
    }

    private fun recherchePays(paysNom: String) {
        runBlocking {
            try {
                if (listeAllPays.isNotEmpty()){
                    val paysFiltre = listeAllPays.filter { unPays -> unPays.commonName.contains(paysNom, ignoreCase = true) }
                    val paysfiltreNom = paysFiltre.map { it.commonName }
                    val adapter = ArrayAdapter<String>(this@JeuBonusActivity, R.layout.activity_jeu_bonus_listview_item, paysfiltreNom)
                    listView.adapter = adapter
                } else {
                    Log.d(TAG, "Liste vide")
                }
            }catch (e: Exception){
                Log.e(TAG,"Erreur lors de la requête API : ${e.message}")
            }
        }
    }

    private fun submitPays(paysSelected : Pays, nbessais: Int) {
        if (paysSelected.commonName == paysatrouver.commonName) {
            Toast.makeText(this, "Bravo, vous avez trouvé le pays en ${nbessais} essais!", Toast.LENGTH_SHORT).show()
            listePaysGuess = listePaysGuess.filter { unPays -> unPays.commonName == paysatrouver.commonName } as ArrayList<Pays>
            listeAllPays = listeAllPays.filter { unPays -> unPays.commonName == "findelapartie" }
            val adapter = ArrayAdapter<String>(this@JeuBonusActivity, R.layout.activity_jeu_bonus_listview_item, listeAllPays.map { it.commonName })
            listView.adapter = adapter
        } else {
            if (nbessais == 4 && (paysSelected.subregion != paysatrouver.subregion)) {
                val TextViewRegion = TextView(this).apply {
                    text = "La région du pays recherché est ${paysatrouver.subregion}"
                    gravity = android.view.Gravity.CENTER
                }
                AlertDialog.Builder(this).apply {
                    setTitle("Indice n°1")
                    setView(TextViewRegion)
                    setNegativeButton("Annuler", null)
                }.show()
            }
            if (nbessais == 6 && (paysSelected.commonName != paysatrouver.commonName)) {
                val imageViewPays = ImageView(this)
                val dialog = AlertDialog.Builder(this).create()
                dialog.setOnShowListener {
                    val params: ViewGroup.LayoutParams = imageViewPays.getLayoutParams()
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = 400
                    imageViewPays.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageViewPays.requestLayout()
                    Glide.with(this).load(paysatrouver.flagUrl).into(imageViewPays)
                }
                dialog.apply {
                    setTitle("Indice n°2")
                    setView(imageViewPays)
                }.show()
            }
            if (nbessais == 8 && (paysSelected.capital != paysatrouver.capital)) {
                val TextViewCapital = TextView(this).apply {
                    text = "La capitale du pays recherché est ${paysatrouver.capital}"
                    gravity = android.view.Gravity.CENTER
                }
                AlertDialog.Builder(this).apply {
                    setTitle("Indice n°3")
                    setView(TextViewCapital)
                    setNegativeButton("Annuler", null)
                }.show()
            }
            if (nbessais == 10 && (paysSelected.commonName[0] != paysatrouver.commonName[0])) {
                val TextViewCapital = TextView(this).apply {
                    text = "La première lettre du pays recherché est ${paysatrouver.commonName[0]}"
                    gravity = android.view.Gravity.CENTER
                }
                AlertDialog.Builder(this).apply {
                    setTitle("Dernier indice !")
                    setView(TextViewCapital)
                    setNegativeButton("Annuler", null)
                }.show()
            }
            if (nbessais == 12) {
                Toast.makeText(this, "Vous avez perdu, le pays recherché était ${paysatrouver.commonName}", Toast.LENGTH_SHORT).show()
                listePaysGuess.clear()
            }
        }
        recyclerView.adapter = JeuBonusAdapter(listePaysGuess, paysatrouver)
    }
}