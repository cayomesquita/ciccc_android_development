package com.example.mycontact.network

import com.example.mycontact.network.resource.ContactApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://randomuser.me/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ContactApiService {

    @GET("?inc=login,name,phone&results=20")
    suspend fun getContacts(): ContactApiResponse
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ContactApi {
    val retrofitService : ContactApiService by lazy { retrofit.create(ContactApiService::class.java) }
}