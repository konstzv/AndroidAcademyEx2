package com.zagulin.mycard.presentation.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class FeedPresenter(val repository: FeedRepository) : MvpPresenter<FeedView>() {

    private val feedPaginator = FeedPaginator(repository)
    //    init {
    private val paginationDisposable = feedPaginator.paginationObservable().subscribeBy(
            onNext = {
                println(it)
                viewState.addNews(it.dataList)
            },
            onError = {
                viewState.showErrorMsg(it.localizedMessage)
            }
    )
//    }

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
