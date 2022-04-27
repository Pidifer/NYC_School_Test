package com.example.nyc_schools_test.model.remote

import com.example.nyc_schools_test.common.BASE_URL
import com.example.nyc_schools_test.common.END_POINT_SAT
import com.example.nyc_schools_test.common.END_POINT_SCHOOLS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NycApi {
    @GET(END_POINT_SCHOOLS)
    suspend fun getSchoolList(): Response<List<SchoolListResponse>>

    @GET(END_POINT_SAT)
    suspend fun getSchoolSat(): Response<List<SchoolSatResponse>>

    companion object{
        fun initRetrofit(): NycApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createClient())
                .build()
                .create(NycApi::class.java)

        }

        private fun createClient(): OkHttpClient{
            return OkHttpClient
                .Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply{
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()

        }
    }
}