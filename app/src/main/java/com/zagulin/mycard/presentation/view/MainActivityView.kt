package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainActivityView:MvpView{

    @StateStrategyType(OneExecutionStateStrategy ::class)
    fun showMainFeed()

    @StateStrategyType(OneExecutionStateStrategy ::class)
    fun showIntro()


    @StateStrategyType(OneExecutionStateStrategy ::class)
    fun showAbout()




}