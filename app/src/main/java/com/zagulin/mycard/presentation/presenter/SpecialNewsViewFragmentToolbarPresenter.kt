package com.zagulin.mycard.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.di.FeedModule
import com.zagulin.mycard.models.NavigationEvents
import com.zagulin.mycard.presentation.view.FeedFragmentToolbarView
import com.zagulin.mycard.presentation.view.SpecialNewsFragmentToolbarView
import com.zagulin.mycard.repositories.MainNavigationInteractor
import toothpick.Toothpick
import javax.inject.Inject

class SpecialNewsViewFragmentToolbarPresenter : MvpPresenter<SpecialNewsFragmentToolbarView>() {

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


    fun callOpenEdit(){
        val event = NavigationEvents.OPEN_EDIT_NEWS

        mainNavigationInteractor.callEvent(event)
    }
}