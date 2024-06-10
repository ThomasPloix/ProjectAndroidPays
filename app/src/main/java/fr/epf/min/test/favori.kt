package fr.epf.min.test

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object DataStoreRepo{

    var FavoriteCountries : ArrayList<Pays> = arrayListOf()
    private val repository : String = "favorite_countries"


     fun save(context: Context){
         val sharedPreferences = context.getSharedPreferences(repository,Context.MODE_PRIVATE )
         val edit = sharedPreferences.edit()
         val script = Gson().toJson(FavoriteCountries)
         edit.putString("favPays",script)
         edit.apply()
    }

    fun read (context: Context) {
        val sharedPreferences = context.getSharedPreferences(repository, Context.MODE_PRIVATE)
        val script = sharedPreferences.getString("favPays", null)
        if (script.isNullOrEmpty()) {
            Toast.makeText(context,"Pas de favoris", Toast.LENGTH_SHORT).show()
        }
        else {
            val type = object : TypeToken<ArrayList<Pays>>() {}.type
            FavoriteCountries.clear()
            FavoriteCountries.addAll(Gson().fromJson(script, type))
        }
    }

}


