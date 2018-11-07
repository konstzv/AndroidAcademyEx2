package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class SpecificNewsPresenter(private val feedRepository: FeedRepository) : MvpPresenter<SpecificNewsView>() {

    private val compositeDisposable = CompositeDisposable()
    fun displayNewsById(id: Int) {
        compositeDisposable.add(feedRepository.getNewsById(id).subscribeBy(
                onSuccess = { viewState.displayNews(it) },
                onError = {}
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
