package com.zagulin.mycard.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
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
import com.zagulin.mycard.repositories.FeedRepositorySingleton
import com.zagulin.mycard.ui.adapters.CategoryAdapter
import com.zagulin.mycard.ui.adapters.FeedAdapter
import kotlinx.android.synthetic.main.feed_activity.*
import kotlinx.android.synthetic.main.feed_activity_toolbar.*


class FeedActivity : MvpAppCompatActivity(), FeedView, OnNewsItemClickListener {

    @InjectPresenter
    lateinit var feedPresenter: FeedPresenter

    @ProvidePresenter
    fun provideFeedPresenter(): FeedPresenter {
        return FeedPresenter(FeedRepositorySingleton.instance)
    }

    private var categoryAdapter: CategoryAdapter? = null
    private var feedAdapter: FeedAdapter? = null
    private var layoutManager: LinearLayoutManager? = null


    override fun onItemClick(item: NewsItem) {
        startActivity(SpecificNewsActivity.intent(this, item.id!!))
    }

    override fun clearFeed() {
        feedAdapter?.run {
            items =  mutableListOf()
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
        feed_activity_toolbar_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoryAdapter?.getItem(position)?.let {
                    feedPresenter.changeCategory(it)
                }

            }

        }
        initToolbar()
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


}