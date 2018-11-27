package com.zagulin.mycard.presentation.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.presentation.view.SpecificNewsDisplayView
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class SpecificNewsPresenter : MvpPresenter<SpecificNewsView>() {



    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showNews()
    }

    fun showEditFragment() {
        viewState.editNews()
    }



}
