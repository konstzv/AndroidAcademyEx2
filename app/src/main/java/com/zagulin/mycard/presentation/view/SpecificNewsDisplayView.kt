package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.zagulin.mycard.models.NewsItem

interface SpecificNewsDisplayView : MvpView {
    fun displayNews(newsItem: NewsItem)
//    fun finish()
    fun showMsg(msg: String)

    fun backAction()

}
