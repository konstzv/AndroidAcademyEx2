package com.zagulin.mycard.presentation.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.R
import com.zagulin.mycard.common.pagination.Paginator
import com.zagulin.mycard.di.FeedModule
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.repositories.FeedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FeedPresenter : MvpPresenter<FeedView>() {

    companion object {
        const val CATEGORY_PREF_NAME = "selectedCategory"
        const val TAG = "FeedPresenter"
    }

    @Inject
    lateinit var repository: FeedRepository

    @Inject
    lateinit var feedPaginator: Paginator<FeedItem>

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var context: Context


    private val compositeDisposable = CompositeDisposable()

    private val tempCompositeDisposable = CompositeDisposable()

    private var isFirstLoadingDone = false


    init {
        val feedScope = Toothpick.openScopes(
                App.Companion.Scopes.APP_SCOPE.name
                , App.Companion.Scopes.FEED_SCOPE.name
        )
        feedScope.installModules(FeedModule)
        Toothpick.inject(this, feedScope)

        compositeDisposable.add(feedPaginator.paginationObservable().subscribeBy(
                onNext = {
                    it.dataList?.let { list ->
                        if (!isFirstLoadingDone) {
                            viewState.showProgress(false)
                        }
                        viewState.addNews(list)
                        viewState.showProgress(false)
                    } ?: it.error?.let { throwable ->
                        handleLoadingFeedError(throwable)

                    } ?: handleLoadingFeedError()

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
        viewState.setSelectedCategory(repository.selectedCategory)

    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress(true)
        getDefaultCtategory(
                {
                    viewState.setSelectedCategory(it)

                }
                , viewState::showErrorMsg)
    }


    private fun getDefaultCtategory(onSuccess: (cat: Category) -> Unit
                                    , onError: (msg: String) -> Unit) {
        val defCategory = sharedPreferences.getInt(CATEGORY_PREF_NAME, 0)
        compositeDisposable.run {
            add(repository.getCategories().subscribeBy(
                    onSuccess = { list ->
                        onSuccess(list.first { it.id == defCategory })
                    },
                    onError = {
                        onError(it.localizedMessage)
                        it.printStackTrace()
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

        repository.selectedCategory = category
        feedPaginator.reload()
        viewState.clearFeed()
        onLoadMore()

        sharedPreferences.edit().putInt(CATEGORY_PREF_NAME, category.id).apply()


    }

    private fun handleLoadingFeedError(throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e(TAG, throwable.localizedMessage)
        }

        viewState.askUserToDoAction(
                context.getString(R.string.network_error)
                , context.getString(R.string.repeart)
                , this::onLoadMore
        )
    }

    fun subscribeOnNewsItem(id: Int) {
        tempCompositeDisposable.add(repository.listenItemUpdate(id).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { viewState.updateNews(it) },
                onError = { println(it) }
        )
        )
    }

    fun clearTempSubscriptions(){
        tempCompositeDisposable.clear()
    }

}
