package com.zagulin.mycard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.feed_activity.*
import kotlinx.android.synthetic.main.feed_activity_toolbar.*


class FeedActivity : AppCompatActivity(), OnNewsItemClickListener {
    companion object {
        private const val ITEMS_PER_PAGE = 10
        private const val TAG = "FeedActivity"
    }

    private var feedAdapter: FeedAdapter? = null
    private var getNewsWithAdsDisposable: Disposable? = null
    private var layoutManager: LinearLayoutManager? = null


    @Volatile
    private var isNewDataLoadingToFeed = false

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

        initPagination()
    }

    @SuppressLint("CheckResult")
    private fun initPagination() {
        val news = LocalFeedRepository().getNews()
        getScrollObservable().subscribeOn(AndroidSchedulers.mainThread()).distinctUntilChanged().subscribeBy(

                onNext = { totalItemCount ->
                    isNewDataLoadingToFeed = true
                    val newItems = mutableListOf<Any>()
                    for (i in 0..ITEMS_PER_PAGE) {
                        newItems.add(news[(totalItemCount + i) % news.size])
                    }
                    feed_activity_recycler.post {
                        feedAdapter?.addItems(newItems)
                        isNewDataLoadingToFeed = false
                    }

                },
                onError = {
                    Log.e(FeedActivity.TAG,it.message)
                }
        )
    }

    private fun getScrollObservable(): Observable<Int> {
        return Observable.create { emitter ->
            feed_activity_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isNewDataLoadingToFeed) {

                        layoutManager?.let {
                            val lastVisibleItem = it.findLastVisibleItemPosition()
                            val totalItemCount = it.itemCount
                            if (totalItemCount <= lastVisibleItem + ITEMS_PER_PAGE / 2 + 1) {
                                emitter.onNext(totalItemCount)
                            }
                        }
                    }


                }


            })
            if (feed_activity_recycler.adapter?.itemCount == 0) {
                emitter.onNext(0)
            }
        }

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
        layoutManager = if (isVertical()) {
            GridLayoutManager(this, calculateHowManyItemsFitOnScreen())
        } else {
            LinearLayoutManager(this)
        }
        feed_activity_recycler.layoutManager = layoutManager
    }

    private fun calculateHowManyItemsFitOnScreen(): Int {
        val widthPixels = resources.displayMetrics.widthPixels
        return widthPixels / resources.getDimensionPixelSize(R.dimen.item_ad_width)
    }

    private fun isVertical(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }


    override fun onStop() {
        super.onStop()
        getNewsWithAdsDisposable?.dispose()
    }


}