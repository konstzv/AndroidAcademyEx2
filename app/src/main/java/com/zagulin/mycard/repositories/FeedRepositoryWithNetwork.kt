package com.zagulin.mycard.repositories

import android.content.Context
import com.zagulin.mycard.App
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.NewsItemNetwork
import com.zagulin.mycard.repositories.api.NewYorkTimesAPIService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import java.net.InetAddress
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import javax.inject.Inject


abstract class FeedRepositoryWithNetwork : FeedRepository {

    private val service = NewYorkTimesAPIService.create()

    @Inject
    lateinit var context:Context

    override fun isServerAvailable(): Single<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return Single.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    fun getDataFromNetwork(category: Category): Single<List<NewsItemNetwork>> {
        return service
                .getTopStories(category.name)
                .map {
                    val res = it.results
                    res ?: emptyList<NewsItemNetwork>()
                }
    }

    init {
        Toothpick.inject(this, Toothpick.openScopes(
                App.Companion.Scopes.APP_SCOPE.name
                , App.Companion.Scopes.FEED_SCOPE.name)
        )
    }

}