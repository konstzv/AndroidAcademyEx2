package com.zagulin.mycard.ui.fragment

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.zagulin.mycard.R
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.presenter.SpecificNewsViewPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsView
import kotlinx.android.synthetic.main.specific_news_view_fragment.*
import kotlinx.android.synthetic.main.specific_news_view_fragment_toolbar.*
import java.util.*


class SpecificNewsViewFragment : MvpAppCompatFragment(), SpecificNewsView {

    companion object {
        private const val EXTRA_NEWS_ITEM_ID = "extra_news_item_id"

        fun newInstance(newItemId: Int): SpecificNewsViewFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_NEWS_ITEM_ID, newItemId)
            val fragment = SpecificNewsViewFragment()
            fragment.arguments = args
            return fragment
        }

        interface OnEditClickListener {
            fun onEditClick()
        }

    }

    @InjectPresenter
    lateinit var presenter: SpecificNewsViewPresenter

    var onEditClickListener: OnEditClickListener? = null

    override fun onCreateView(
            inflater: LayoutInflater
            , container: ViewGroup?
            , savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.specific_news_view_fragment, container, false)
    }

    override fun displayNews(newsItem: NewsItem) {

        specific_news_view_fragment_text_view_title.setText(newsItem.title)
        specific_news_view_fragment_text_view_article.setText(newsItem.fullText)
        val thumbnailRequest = Glide
                .with(this)
                .load(newsItem.thumbnailUrl)

        Glide.with(this)
                .load(newsItem.imageUrl)
                .thumbnail(thumbnailRequest)
                .into(specific_news_view_fragment_image)

        newsItem.publishDate?.let {
            specific_news_view_fragment_text_view_date.text = DateUtils.getRelativeTimeSpanString(
                    it.time
                    , Calendar.getInstance().time.time
                    , 0L
                    , DateUtils.FORMAT_ABBREV_ALL)
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setActionBar(specific_news_view_fragment_toolbar)

        specific_news_activity_toolbar_image_edit.setOnClickListener {
            onEditClickListener?.onEditClick()
        }
        specific_news_activity_toolbar_image_delete.setOnClickListener {
            presenter.removeItem()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.subcribeOnNewsItem(it.getInt(EXTRA_NEWS_ITEM_ID))
        }


    }


}