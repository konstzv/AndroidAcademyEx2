package com.zagulin.mycard

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.feed_activity.*
import kotlinx.android.synthetic.main.feed_activity_toolbar.*

class FeedActivity : AppCompatActivity(), OnNewsItemClickListener {
    override fun onItemClick(item: NewsItem) {
        startActivity(SpecificNewsActivity.intent(this, item))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)
        initRecycle()
        initToolbar()
    }

    private fun initToolbar() {
        feed_activity_toolbar_text_view_title.text = title
        feed_activity_toolbar_image_about.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun initRecycle() {
        feed_activity_recycler.adapter = FeedAdapter(LocalFeedRepository().getNewsWithAds(), this)
        feed_activity_recycler.addItemDecoration(ItemOffsetDecoration(this, R.dimen.short_indent))
        if (isVertical()) {
            feed_activity_recycler.layoutManager = GridLayoutManager(this, calculateHowManyItemsFitOnScreen())
        } else {
            feed_activity_recycler.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun calculateHowManyItemsFitOnScreen(): Int {
        val widthPixels = resources.displayMetrics.widthPixels
        return widthPixels / resources.getDimensionPixelSize(R.dimen.item_ad_width)
    }

    private fun isVertical(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }


}