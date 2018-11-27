package com.zagulin.mycard.presentation.presenter

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.view.SpecificNewsEditView
import com.zagulin.mycard.repositories.FeedRepository
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

    @Inject
    lateinit var context: Context

    init {
        Toothpick.inject(this, Toothpick.openScopes(
                App.Companion.Scopes.APP_SCOPE.name
                , App.Companion.Scopes.FEED_SCOPE.name
        ))
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
            it.previewText = article
            it.publishDate = date
            compositeDisposable.add(repository.updateItem(it).subscribeBy(
                    onComplete = {
                        viewState.finish()
                    },
                    onError = {
                        viewState.showMsg("Ошибка при изменение новости")
                    }
            ))
        }

    }
}
