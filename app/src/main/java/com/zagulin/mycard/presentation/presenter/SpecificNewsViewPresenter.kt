package com.zagulin.mycard.presentation.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class SpecificNewsViewPresenter : MvpPresenter<SpecificNewsView>() {

    @Inject
    lateinit var repository: FeedRepository

    init {
        Toothpick.inject(this, Toothpick.openScope(App.Companion.Scopes.FEED_SCOPE.name))
    }

    private val compositeDisposable = CompositeDisposable()

    var id = 0


    fun subcribeOnNewsItem(id: Int) {
        this.id = id
        compositeDisposable.add(
                repository.listenItemUpdate(id).observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(onNext = {
                            Log.d("SpecificNewsPresenter","SUBSCRIBE")
                            it.value?.let { viewState.displayNews(it) } })
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun removeItem() {
        repository.removeItem(id).subscribeBy()
    }
}
