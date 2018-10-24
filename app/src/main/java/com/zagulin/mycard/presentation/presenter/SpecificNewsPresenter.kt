package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.repositories.LocalFeedRepository
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class SpecificNewsPresenter : MvpPresenter<SpecificNewsView>() {

    val localFeedRepository = LocalFeedRepository()

    fun displayNewsById(id: Int) {
        localFeedRepository.getNewsById(id).subscribeBy(
                onSuccess = { viewState.displayNews(it) },
                onError = {}
        )
    }


}
