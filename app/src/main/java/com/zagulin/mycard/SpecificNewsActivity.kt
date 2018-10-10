package com.zagulin.mycard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.specific_news_activity.*
import java.util.*


class SpecificNewsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_NEWS_ITEM = "extra_news_item"
        fun intent(context: Context, newsItem: NewsItem): Intent {
            val intent = Intent(context, SpecificNewsActivity::class.java)
            intent.putExtra(EXTRA_NEWS_ITEM, newsItem)
            return intent
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_news_activity)
        val newsItem = intent.getParcelableExtra<NewsItem>(EXTRA_NEWS_ITEM)

        newsItem?.let {
            supportActionBar?.title = it.category?.name
            specific_news_activity_text_view_title.text = newsItem.title
            specific_news_activity_text_view_article.text = newsItem.fullText
            Glide.with(this).load(newsItem.imageUrl).into(specific_news_activity_image)
            it.publishDate?.let {
                specific_news_activity_text_view_date.text =
                        DateUtils.getRelativeTimeSpanString(it.time, Calendar.getInstance().time.time,
                                0L, DateUtils.FORMAT_ABBREV_ALL)
            }
        }

    }


}