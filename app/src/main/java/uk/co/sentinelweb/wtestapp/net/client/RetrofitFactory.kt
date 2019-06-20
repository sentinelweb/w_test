package uk.co.sentinelweb.wtestapp.net.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.sentinelweb.wtestapp.net.service.CakeListService


class RetrofitFactory {

    companion object {
        internal const val BASE_URL = "https://gist.githubusercontent.com/"
    }

    fun createClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun createCakeListService(retrofit: Retrofit): CakeListService =
        retrofit.create<CakeListService>(CakeListService::class.java)

}