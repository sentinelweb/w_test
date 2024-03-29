package uk.co.sentinelweb.wtestapp.net.service

import retrofit2.Call
import retrofit2.http.GET
import uk.co.sentinelweb.wtestapp.domain.Cake


interface CakeListService {

    @GET("t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client")
    suspend fun listCakes(): List<Cake>
}