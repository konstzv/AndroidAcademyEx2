package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView

interface FeedView : MvpView {
    fun addNews(list:List<Any>)
    fun setNews(list:List<Any>)
    fun showErrorMsg(errorMsg:String)
}
