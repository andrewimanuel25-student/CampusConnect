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
    suspend fun getCountries():
            Result<List<NegaraMitra>> {
                return try {
                    val response = apolloClient
                        .query(GetCountriesQuery())
                        .execute()

                    if (response.hasErrors()) {
                        Result.Error(
                            response.errors
                                ?.joinToString {
                                    it.message
                                }
                                ?: "GraphQL mengembalikan kesalahan"
                        )
                    } else {
                        val data = response.data
                            ?.countries
                            ?.mapNotNull { country ->
                                if (
                                    country.name == null ||
                                    country.code == null
                                ) {
                                    null
                                } else {
                                    NegaraMitra(
                                        nama = country.name,
                                        kode = country.code,
                                        ibuKota =
                                            country.capital ?: "-"
                                    )
                                }
                            }
                            ?: emptyList()
                        Result.Success(data)
                    }
                } catch (e: Exception) {
                    Result.Error(
                        "Gagal mengambil data negara: ${e.message}"
                    )
                }
    }
}