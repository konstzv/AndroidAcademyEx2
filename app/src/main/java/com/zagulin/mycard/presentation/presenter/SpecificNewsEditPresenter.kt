package com.zagulin.mycard.presentation.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.view.SpecificNewsEditView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

@InjectViewState
class SpecificNewsEditPresenter : MvpPresenter<SpecificNewsEditView>() {

    @Inject
    lateinit var repository: FeedRepository


    init {
        Toothpick.inject(this, Toothpick.openScope(App.Companion.Scopes.FEED_SCOPE.name))
    }

    private var newsItem: NewsItem? = null
    private val compositeDisposable = CompositeDisposable()
    fun displayNewsById(id: Int) {
        compositeDisposable.add(repository.getNewsById(id).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    newsItem = it
                    viewState.displayNews(it)
                },
                onError = { println(it) }
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    fun saveChanges(title: String, article: String, date: Date) {
        newsItem?.let {
            it.title = title
            it.fullText = article
            it.publishDate = date
             repository.updateItem(it).subscribeBy (
                     onComplete = {
                         Log.d("TEST","COMPLETE")
                     },
                     onError = {
                         Log.d("TEST",it.localizedMessage)
                     }
             )
        }

    }
}
