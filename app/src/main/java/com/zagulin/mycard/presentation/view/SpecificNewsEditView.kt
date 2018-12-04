package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.zagulin.mycard.models.NewsItem


interface SpecificNewsEditView : MvpView {
    fun displayNews(newsItem: NewsItem)
    fun showMsg(msg: String)
//    fun finish()
    fun backAction()
}
