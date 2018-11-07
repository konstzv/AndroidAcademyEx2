package com.zagulin.mycard.presentation.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
open class FeedPresenter(private val repository: FeedRepository) : MvpPresenter<FeedView>() {

    private val compositeDisposable = CompositeDisposable()
    private val feedPaginator = FeedPaginator(repository)

    init {
        compositeDisposable.run {
            add(repository.getCategories().subscribeBy(
                    onSuccess = {
                        repository.setCategory(it[0])
                    },
                    onError = {
                        viewState.showErrorMsg(it.localizedMessage)
                    }
            ))
            add(feedPaginator.paginationObservable().subscribeBy(
                    onNext = {
                        viewState.addNews(it.dataList)
                    },
                    onError = {
                        viewState.showErrorMsg(it.localizedMessage)
                    }
            ))
        }
    }

    fun showCategories() {
        compositeDisposable.add(repository.getCategories().subscribeBy(
                onSuccess = {
                    viewState.showCategoriesList(it.toMutableList())
                },
                onError = {
                    viewState.showErrorMsg(it.localizedMessage)
                }

        ))


    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onLoadMore()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    @SuppressLint("CheckResult")
    fun onLoadMore() {
        feedPaginator.loadNextPage()
    }

    fun changeCategory(category: Category) {
        repository.setCategory(category)
        feedPaginator.reload()
        viewState.clearFeed()
        onLoadMore()
    }


}
