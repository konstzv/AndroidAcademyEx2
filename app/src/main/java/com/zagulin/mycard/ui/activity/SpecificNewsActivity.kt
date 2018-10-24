package com.zagulin.mycard.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.zagulin.mycard.R
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.presenter.SpecificNewsPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsView
import kotlinx.android.synthetic.main.specific_news_activity.*
import java.util.*

class SpecificNewsActivity : MvpAppCompatActivity(), SpecificNewsView {

    @InjectPresenter
    lateinit var  specificNewsPresenter: SpecificNewsPresenter

    companion object {
        private const val EXTRA_NEWS_ITEM_ID = "extra_news_item_id"
        fun intent(context: Context, newsItemId: Int): Intent {
            val intent = Intent(context, SpecificNewsActivity::class.java)
            intent.putExtra(EXTRA_NEWS_ITEM_ID, newsItemId)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_news_activity)
        val newsItemId = intent.getIntExtra(EXTRA_NEWS_ITEM_ID, 0)
        specificNewsPresenter.displayNewsById(newsItemId)

    }

    override fun displayNews(newsItem: NewsItem) {
        supportActionBar?.title = newsItem.category?.name
        specific_news_activity_text_view_title.text = newsItem.title
        specific_news_activity_text_view_article.text = newsItem.fullText
        Glide.with(this).load(newsItem.imageUrl).into(specific_news_activity_image)
        newsItem.publishDate?.let {
            specific_news_activity_text_view_date.text =
                    DateUtils.getRelativeTimeSpanString(it.time, Calendar.getInstance().time.time,
                            0L, DateUtils.FORMAT_ABBREV_ALL)
        }
    }


}