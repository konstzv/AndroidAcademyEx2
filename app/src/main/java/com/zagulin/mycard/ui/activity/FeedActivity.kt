package com.zagulin.mycard.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.zagulin.mycard.R
import com.zagulin.mycard.common.ItemOffsetDecoration
import com.zagulin.mycard.common.OnNewsItemClickListener
import com.zagulin.mycard.common.pagination.RecyclerViewPagination
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.presenter.FeedPresenter
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.ui.adapters.CategoryAdapter
import com.zagulin.mycard.ui.adapters.FeedAdapter
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import kotlinx.android.synthetic.main.feed_activity.*
import kotlinx.android.synthetic.main.feed_activity_toolbar.*


class FeedActivity : MvpAppCompatActivity(), FeedView, OnNewsItemClickListener {
    override fun updateNews(newsItem: NewsItem) {
        feedAdapter?.let {feedAdapter ->
            val indexes = Observable.range(0, feedAdapter.items.size)
            Observable.fromIterable(feedAdapter.items)
                    .zipWith(indexes).filter{
                        val item = it.first
                        item is NewsItem && item.id == newsItem.id
                    }.subscribeBy (
                           onNext = {
                               feedAdapter.updateItem(it.second,newsItem)
                           }
                    )
//            for (i in 0 until it.items.size) { // equivalent of 1 <= i && i <= 10
//                if (it.items )
//            }
        }

    }

    override fun askUserToDoAction(msg: String, actionName: String, action: () -> Unit) {
        Snackbar.make(
                feed_activity_text_root,
                msg,
                Snackbar.LENGTH_INDEFINITE
        ).setAction(actionName,{action.invoke()}
        ).show()
    }



    override fun setSelectedCategory(category: Category) {
        categoryAdapter?.let {
            feed_activity_toolbar_spinner.setSelection(it.getPosition(category))
        }

    }

    @InjectPresenter
    lateinit var feedPresenter: FeedPresenter


    private var categoryAdapter: CategoryAdapter? = null
    private var feedAdapter: FeedAdapter? = null
    private var layoutManager: LinearLayoutManager? = null


    override fun onItemClick(item: NewsItem) {
        item.id?.let {
            feedPresenter.subscribeOnNewsItem(it)
            startActivity(SpecificNewsActivity.intent(this, it))
        }


    }



    override fun clearFeed() {
        feedAdapter?.run {
            items = mutableListOf()
            notifyDataSetChanged()
        }
    }

    override fun showCategoriesList(list: MutableList<Category>) {
        categoryAdapter = CategoryAdapter(context = applicationContext, objects = list)
        feed_activity_toolbar_spinner.adapter = categoryAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)
        initRecycle()

        initToolbar()

    }

    override fun showProgress(isVisible:Boolean){
        feed_activity_progress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun initCategorySpinnerItemSelectListener() {
        feed_activity_toolbar_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoryAdapter?.getItem(position)?.let {
                    feedPresenter.changeCategory(it)
                }

            }

        }
    }

    override fun addNews(list: List<FeedItem>) {

        feedAdapter?.addItems(list)

    }


    override fun showErrorMsg(msg: String) {
        Snackbar.make(
                window.decorView.rootView,
                msg,
                Snackbar.LENGTH_LONG
        ).show()
    }

    private fun initToolbar() {
        feed_activity_toolbar_text_view_title.text = title
        feed_activity_toolbar_image_about.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        initPagination()
        feedPresenter.showCategories()
        initCategorySpinnerItemSelectListener()
    }

    private fun initPagination() {
        val pageListener = RecyclerViewPagination { feedPresenter.onLoadMore() }
        feed_activity_recycler.addOnScrollListener(pageListener)
    }


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

    override fun onResume() {
        super.onResume()
        feedPresenter.clearTempSubscriptions()
    }


}