package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.di.FeedModule
import com.zagulin.mycard.models.NavigationEvents
import com.zagulin.mycard.presentation.view.FeedMainFragmentView
import com.zagulin.mycard.repositories.MainNavigationInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FeedMainPresenter : MvpPresenter<FeedMainFragmentView>() {


    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var mainNavigationInteractor: MainNavigationInteractor

    var currentId: Int? = null

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
        viewState.showFeed()

        compositeDisposable.add(mainNavigationInteractor
                .getNavigationEventObservable()
                .filter {
                    it == NavigationEvents.OPEN_NEWS || it == NavigationEvents.OPEN_EDIT_NEWS
                }
                .subscribeBy(
                        onNext = {
                            when (it) {
                                NavigationEvents.OPEN_EDIT_NEWS
                                -> {


                                    currentId?.let {
                                        id->
                                        viewState.showEdit(id)
                                    }
                                }

                                NavigationEvents.OPEN_NEWS
                                -> {
                                    currentId = mainNavigationInteractor.selectedNewsId
                                    currentId?.let(viewState::showNews)

                                }
                            }

                        }
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
