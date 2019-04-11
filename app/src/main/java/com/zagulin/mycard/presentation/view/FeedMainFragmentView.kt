package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface FeedMainFragmentView:MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showFeed()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEdit(id:Int)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNews(id:Int)
}
