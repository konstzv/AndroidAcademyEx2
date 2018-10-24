package com.zagulin.mycard.presentation.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.repositories.LocalFeedRepository
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class FeedPresenter : MvpPresenter<FeedView>() {

    private val feedPaginator = FeedPaginator(LocalFeedRepository())
    init {
        feedPaginator.source.subscribeBy(
                onNext = {
                    println(it)
                    viewState.addNews(it.dataList)
                }
        )
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onLoadMore()
    }


    @SuppressLint("CheckResult")
    fun onLoadMore() {
        feedPaginator.loadNextPage()
    }


}
