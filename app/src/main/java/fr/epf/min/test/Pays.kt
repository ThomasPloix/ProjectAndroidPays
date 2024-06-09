package fr.epf.min.test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Pays(
    val commonName: String,
    val officialName: String,
    val region: String,
    val subregion: String,
    val language: String,
    val capital: String,
    val currency: String,
    val population: Int,
    val area: Double,
    val flagUrl: String,
    var favori: Boolean
) : Parcelable {
    companion object {

        fun generate(size : Int = 20) =
            (1..size).map {
                Pays("Nom${it}",
                    "Prénom${it}",
                    "region${it}",
                    "subregion${it}",
                    "langage${it}",
                    "capital${it}",
                    "currency${it}",
                    69,
                    3939392.2,
                    "flag${it}",
                    false)
            }
    }

}