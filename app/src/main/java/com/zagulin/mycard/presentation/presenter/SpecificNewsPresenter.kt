package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class SpecificNewsPresenter : MvpPresenter<SpecificNewsView>() {

    @Inject
    lateinit var repository: FeedRepository

    init {
        Toothpick.inject(this, Toothpick.openScope(App.Companion.Scopes.FEED_SCOPE.name))
    }

    private val compositeDisposable = CompositeDisposable()
    fun displayNewsById(id: Int) {
        compositeDisposable.add(repository.getNewsById(id).subscribeBy(
                onSuccess = { viewState.displayNews(it) },
                onError = { println(it) }
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
