package com.zagulin.mycard.presentation.view

import com.arellomobile.mvp.MvpView
import com.zagulin.mycard.models.Category


interface FeedFragmentToolbarView:MvpView{
   fun setCategories(categories:MutableList<Category>)
   fun setSelectedCategory(category: Category)
}