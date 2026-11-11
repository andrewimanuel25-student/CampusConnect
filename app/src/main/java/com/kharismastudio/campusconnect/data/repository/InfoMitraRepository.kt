package com.kharismastudio.campusconnect.data.repository

import com.apollographql.apollo.ApolloClient
import com.kharismastudio.campusconnect.graphql.GetCountriesQuery

data class NegaraMitra(
    val nama: String,
    val kode: String,
    val ibuKota: String
)

class InfoMitraRepository(
    private val apolloClient: ApolloClient
) {
    suspend fun getCountries(): Result<List<NegaraMitra>> {
        return try {
            val response = apolloClient
                .query(GetCountriesQuery())
                .execute()

            val dataResponse = response.data
            if (response.hasErrors() || dataResponse == null) {
                Result.Error(
                    response.errors?.joinToString { it.message } ?: "Gagal mengambil data dari server"
                )
            } else {
                val listNegara = dataResponse.countries.mapNotNull { country ->
                    // Memastikan data nama dan kode tersedia
                    val name = country.name
                    val code = country.code
                    if (name != null && code != null) {
                        NegaraMitra(
                            nama = name,
                            kode = code,
                            ibuKota = country.capital ?: "-"
                        )
                    } else {
                        null
                    }
                }
                Result.Success(listNegara)
            }
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.localizedMessage}")
        }
    }
}
