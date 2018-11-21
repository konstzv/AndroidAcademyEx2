package com.zagulin.mycard.presentation.presenter

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


    fun saveChanges(title: String, article: String): Completable? {
        newsItem?.let {
            it.title = title
            it.fullText = article
            return repository.updateItem(it)
        } ?: return null

    }
}
