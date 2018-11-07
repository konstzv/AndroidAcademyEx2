package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem

interface FeedView : MvpView {
    fun addNews(list: List<FeedItem>)

    fun showErrorMsg(errorMsg: String)
    fun showCategoriesList(list: MutableList<Category>)
    fun clearFeed()
}
