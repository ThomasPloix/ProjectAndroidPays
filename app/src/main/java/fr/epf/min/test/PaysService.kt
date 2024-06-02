package fr.epf.min.test

import retrofit2.http.GET
import retrofit2.http.Path

interface PaysService {
    @GET("name/{name}")
    suspend fun getUnPays(@Path("name") nomPays: String) : List<Country>
    @GET("all")
    suspend fun getAllPaysAllFields() : List<Country>
    @GET("independent?status=true&fields=name,region,subregion,languages,capital,currencies,population,flags,area")
    suspend fun getAllPays() : List<Country>
    @GET("subregion/{subregion}")
    suspend fun getPaysBySubregion(@Path("subregion") sousRegion: String) : List<Country>
}

data class Country(
    val name: Name,
    val region: String,
    val capital: List<String>,
    val currencies: Map<String, Currency>,
    val population: Int,
    val flags: Flags
)
data class Name(val common: String, val official: String)
data class Currency(val name: String, val symbol: String)
data class Flags(val png: String)
