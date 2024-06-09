package fr.epf.min.test

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object DataStoreRepo{

    var FavoriteCountries : ArrayList<Pays> = arrayListOf()
    val repository : String = "favorite_countries"


     fun save(context: Context){
         val sharedPreferences = context.getSharedPreferences(repository,Context.MODE_PRIVATE )
         val edit = sharedPreferences.edit()
         val script = Gson().toJson(FavoriteCountries)
         edit.putString("favPays",script)
         edit.apply()
    }

    suspend fun read (context: Context) {
        val sharedPreferences = context.getSharedPreferences(repository, Context.MODE_PRIVATE)
        val script = sharedPreferences.getString("favPays", null)
        if (script != null) {
            val type = object : TypeToken<ArrayList<Pays>>() {}.type
            FavoriteCountries.clear()
            FavoriteCountries.addAll(Gson().fromJson(script, type))
        }
    }

}


