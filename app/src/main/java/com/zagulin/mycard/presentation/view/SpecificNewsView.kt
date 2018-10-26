package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.zagulin.mycard.models.NewsItem

interface SpecificNewsView : MvpView {
    fun displayNews(newsItem: NewsItem)

}
