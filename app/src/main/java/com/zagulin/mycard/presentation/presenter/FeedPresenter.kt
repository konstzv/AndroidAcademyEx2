package com.zagulin.mycard.presentation.presenter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.di.FeedModule
import com.zagulin.mycard.common.pagination.Paginator
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FeedPresenter : MvpPresenter<FeedView>() {

    companion object {
        const val CATEGORY_PREF_NAME = "category"
    }

    @Inject
    lateinit var repository: FeedRepository

    @Inject
    lateinit var feedPaginator: Paginator<FeedItem>

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    private val compositeDisposable = CompositeDisposable()


    init {
        val feedScope = Toothpick.openScopes(App.Companion.Scopes.APP_SCOPE.name, App.Companion.Scopes.FEED_SCOPE.name)
        feedScope.installModules(FeedModule)
        Toothpick.inject(this, feedScope)

        compositeDisposable.add(feedPaginator.paginationObservable().subscribeBy(
                onNext = {
                    viewState.addNews(it.dataList)
                },
                onError = {
                    viewState.showErrorMsg(it.localizedMessage)
                }
        ))
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
        viewState.setSelectedCategory(repository.getCategory())

    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDefaultCtategory(
                {
                    viewState.setSelectedCategory(it)
                    onLoadMore()
                }
                , viewState::showErrorMsg)
    }


    private fun getDefaultCtategory(onSuccess: (cat: Category) -> Unit, onError: (msg: String) -> Unit) {
        val defCategory = sharedPreferences.getInt(CATEGORY_PREF_NAME, 0)
        compositeDisposable.run {
            add(repository.getCategories().subscribeBy(
                    onSuccess = { list ->
                        onSuccess(list.first { it.id == defCategory })
                    },
                    onError = {
                        onError(it.localizedMessage)
                    }
            ))

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(App.Companion.Scopes.FEED_SCOPE)
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
        sharedPreferences.edit().putInt(CATEGORY_PREF_NAME, category.id).apply()

    }


}
