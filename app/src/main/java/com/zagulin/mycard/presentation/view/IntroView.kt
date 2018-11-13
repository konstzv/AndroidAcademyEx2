package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.zagulin.mycard.models.NewsItem

interface IntroView : MvpView {
    fun moveToFeedActivity()
    fun showIntroActivity()
}
