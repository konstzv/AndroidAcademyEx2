package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import io.reactivex.Observable

@StateStrategyType(OneExecutionStateStrategy::class)
interface FeedView : MvpView {
//    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addNews(list: List<FeedItem>)
//    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMsg(msg: String)
//    fun showCategoriesList(list: MutableList<Category>)
    fun clearFeed()
//    fun setSelectedCategory(category: Category)
//    @StateStrategyType(OneExecutionStateStrategy::class)
    fun askUserToDoAction(msg: String, actionName: String, action: () -> Unit)
    fun showProgress(isVisible: Boolean)
    fun updateNews(it: NewsItem)
    fun removeNews(id: Int)

}
