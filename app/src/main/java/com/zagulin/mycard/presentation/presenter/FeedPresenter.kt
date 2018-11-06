package com.zagulin.mycard.presentation.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
open class FeedPresenter(repository: FeedRepository) : MvpPresenter<FeedView>() {

    private val feedPaginator = FeedPaginator(repository)
    private val paginationDisposable = feedPaginator.paginationObservable().subscribeBy(
            onNext = {
                viewState.addNews(it.dataList)
            },
            onError = {
                viewState.showErrorMsg(it.localizedMessage)
            }
    )


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onLoadMore()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!paginationDisposable.isDisposed)
            paginationDisposable.dispose()
    }

    @SuppressLint("CheckResult")
    fun onLoadMore() {
        feedPaginator.loadNextPage()
    }


}
