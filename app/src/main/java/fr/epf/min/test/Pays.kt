package fr.epf.min.test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Pays(
    val commonName: String,
    val officialName: String,
    val region: String,
    val capital: String,
    val currency: String,
    val population: Int,
    val flagUrl: String
) : Parcelable {
    companion object {

        fun generate(size : Int = 20) =
            (1..size).map {
                Pays("Nom${it}",
                    "Prénom${it}",
                    "region${it}",
                    "capital${it}",
                    "currency${it}",
                    69,
                    "flag${it}")
            }
    }
}