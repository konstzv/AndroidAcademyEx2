package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zagulin.mycard.models.NewsItem

interface SpecificNewsView : MvpView {

    fun showNews()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun editNews()

}
