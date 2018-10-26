package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class SpecificNewsPresenter(private val feedRepository: FeedRepository) : MvpPresenter<SpecificNewsView>() {


    fun displayNewsById(id: Int) {
        feedRepository.getNewsById(id).subscribeBy(
                onSuccess = { viewState.displayNews(it) },
                onError = {}
        )
    }


}
