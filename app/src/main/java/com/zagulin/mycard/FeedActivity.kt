package com.zagulin.mycard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.feed_activity.*


class FeedActivity : AppCompatActivity(), OnNewsItemClickListener {

    private var feedAdapter: FeedAdapter? = null
    private var getNewsWithAdsDisposable: Disposable? = null

    override fun onItemClick(item: NewsItem) {
        startActivity(SpecificNewsActivity.intent(this, item))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)
        initRecycle()
        populateFeed()
    }

    @SuppressLint("CheckResult")
    private fun populateFeed() {

        LocalFeedRepository().getNewsAndAdsPeriodically().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { it ->
                    feedAdapter?.insertItem(0, it)
                    feed_activity_recycler.smoothScrollToPosition(0);

                },
                onError = {
                    println(it)

                }
        )

    }

//    private fun showProgressBar(isVisible: Boolean) {
//        feed_activity_progress.visibility = if (isVisible) View.VISIBLE else View.GONE
//    }

    private fun initRecycle() {
        feedAdapter = FeedAdapter(onNewsItemClickListener = this)
        feed_activity_recycler.adapter = feedAdapter
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
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feed_screen_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {

        R.id.feedAboutMenu -> {
            startActivity(Intent(this, AboutActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        getNewsWithAdsDisposable?.dispose()
    }


}