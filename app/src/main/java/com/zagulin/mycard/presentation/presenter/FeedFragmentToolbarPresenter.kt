package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.di.FeedModule
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.NavigationEvents
import com.zagulin.mycard.presentation.view.FeedFragmentToolbarView
import com.zagulin.mycard.repositories.FeedRepository
import com.zagulin.mycard.repositories.MainNavigationInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FeedFragmentToolbarPresenter : MvpPresenter<FeedFragmentToolbarView>() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var repository: FeedRepository

    @Inject
    lateinit var mainNavigationInteractor: MainNavigationInteractor

    init {
        val feedScope = Toothpick.openScopes(
                App.Companion.Scopes.APP_SCOPE.name
                , App.Companion.Scopes.FEED_SCOPE.name
        )
        feedScope.installModules(FeedModule)
        Toothpick.inject(this, feedScope)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        compositeDisposable.addAll(
                repository.getCategories()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onSuccess = {
                                    viewState.setCategories(it.toMutableList())
                                },
                                onError = {
                                    //                            viewState.showMsg(it.localizedMessage)
                                }

                        ),
                repository.getSelectedCategoryObserbable().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                        onNext = {
                            viewState.setSelectedCategory(it)
                        }
                )
        )
    }

    fun changeCategory(category: Category) {
        repository.selectedCategory = category
    }

    fun callOpenAbout(){
        mainNavigationInteractor.callEvent(NavigationEvents.OPEN_ABOUT)
    }

}