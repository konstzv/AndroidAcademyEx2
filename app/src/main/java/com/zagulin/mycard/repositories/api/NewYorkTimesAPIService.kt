package com.zagulin.mycard.repositories.api

import com.zagulin.mycard.BuildConfig
import com.zagulin.mycard.models.GetTopStoriesNetworkResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface NewYorkTimesAPIService {

    @GET("topstories/v2/{section}.json")
    fun getTopStories(
            @Path("section") address: String
    ): Single<GetTopStoriesNetworkResponse>


    companion object Factory {
        private const val HEADER_API_KEY_NAME = "api-key"
        private const val BASE_URL = "https://api.nytimes.com/svc/"

        fun create(): NewYorkTimesAPIService {

            val client = OkHttpClient.Builder()

                    .addInterceptor { chain ->
                        val req = chain.request().newBuilder().addHeader(HEADER_API_KEY_NAME, BuildConfig.API_KEY)
                        chain.proceed(req.build())
                    }

                    .build()


            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(NewYorkTimesAPIService::class.java)
        }
    }

}