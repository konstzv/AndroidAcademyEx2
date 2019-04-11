package com.zagulin.mycard.ui.fragment.news.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.presentation.presenter.FeedFragmentToolbarPresenter
import com.zagulin.mycard.presentation.presenter.SpecialNewsViewFragmentToolbarPresenter
import com.zagulin.mycard.presentation.view.FeedFragmentToolbarView
import com.zagulin.mycard.presentation.view.SpecialNewsFragmentToolbarView
import com.zagulin.mycard.ui.adapters.CategoryAdapter
import com.zagulin.mycard.ui.fragment.MvpAppCompatFragment
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.feed_fragment_toolbar.*
import kotlinx.android.synthetic.main.specific_news_view_fragment_toolbar.*
import java.util.*


class SpecialNewsEditFragmentToolbar : MvpAppCompatFragment(){
//
//    @InjectPresenter
//    lateinit var presenter: SpecialNewsViewFragmentToolbarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.specific_news_edit_fragment_toolbar,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        specific_news_activity_toolbar_image_edit.setOnClickListener{
//            presenter.callOpenEdit()
//        }
    }
}