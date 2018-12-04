package com.zagulin.mycard.ui.fragment.news

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.zagulin.mycard.R
import com.zagulin.mycard.common.ItemOffsetDecoration
import com.zagulin.mycard.common.pagination.RecyclerViewPagination
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.presenter.FeedPresenter
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.ui.activity.AboutActivity
import com.zagulin.mycard.ui.adapters.CategoryAdapter
import com.zagulin.mycard.ui.adapters.FeedAdapter
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.MvpAppCompatFragment
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_fragment_toolbar.*


class FeedFragment : BaseFragment(), FeedView {


    companion object {
        fun newInstance(): FeedFragment {
            val fragment = FeedFragment()
            return fragment
        }

        interface OnAboutButtonClickCallback{
            fun onAction()
        }
    }


    var onAboutButtonClickCallback:OnAboutButtonClickCallback? = null

    val newsItemClickPublishObservable: Observable<NewsItem>? by lazy {
        feedAdapter.getNewsItemClickPublishObservable()
    }

    override fun removeNews(id: Int) {
        feedAdapter.let { feedAdapter ->
            val indexes = Observable.range(0, feedAdapter.items.size)
            Observable.fromIterable(feedAdapter.items)
                    .zipWith(indexes).filter {
                        val item = it.first
                        item is NewsItem && item.id == id
                    }.subscribeBy(
                            onNext = {
                                feedAdapter.removeItem(it.second)
                            }
                    )

        }
    }

    override fun updateNews(newsItem: NewsItem) {
        feedAdapter.let { feedAdapter ->
            val indexes = Observable.range(0, feedAdapter.items.size)
            Observable.fromIterable(feedAdapter.items)
                    .zipWith(indexes).filter {
                        val item = it.first
                        item is NewsItem && item.id == newsItem.id
                    }.subscribeBy(
                            onNext = {
                                feedAdapter.updateItem(it.second, newsItem)
                            }
                    )
        }

    }

    override fun askUserToDoAction(msg: String, actionName: String, action: () -> Unit) {
        Snackbar.make(
                feed_fragment_root,
                msg,
                Snackbar.LENGTH_INDEFINITE
        ).setAction(actionName, { action.invoke() }
        ).show()
    }


    override fun setSelectedCategory(category: Category) {
        categoryAdapter?.let {
            feed_fragment_toolbar_spinner.setSelection(it.getPosition(category))
        }

    }

    @InjectPresenter
    lateinit var feedPresenter: FeedPresenter


    private var categoryAdapter: CategoryAdapter? = null
    private var feedAdapter: FeedAdapter = FeedAdapter()
    private var layoutManager: LinearLayoutManager? = null


//    override fun onItemClick(item: NewsItem) {
//        item.id.let {
//            feedPresenter.subscribeOnNewsItem(it)
////            startActivity(SpecificNewsActivity.intent(context!!, it))
//        }
//
//
//    }


    override fun clearFeed() {
        feedAdapter.run {
            items = mutableListOf()
            notifyDataSetChanged()
        }
    }

    override fun showCategoriesList(list: MutableList<Category>) {
        context?.let {
            categoryAdapter = CategoryAdapter(context = it, objects = list)
            feed_fragment_toolbar_spinner.adapter = categoryAdapter
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.feed_fragment, container, false)



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycle()
//
        initToolbar()
//
        feed_fragment_update_btn.setOnClickListener {
            feedPresenter.update()
        }
    }

    override fun showProgress(isVisible: Boolean) {
        feed_fragment_progress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun initCategorySpinnerItemSelectListener() {
        feed_fragment_toolbar_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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


    override fun showMsg(msg: String) {

        Snackbar.make(
                feed_fragment_root,
                msg,
                Snackbar.LENGTH_LONG
        ).show()


    }

    private fun initToolbar() {
//        feed_fragment_toolbar_text_view_title.text = title
        feed_fragment_toolbar_image_about.setOnClickListener {
//            startActivity(Intent(context, AboutActivity::class.java))
            onAboutButtonClickCallback?.onAction()
        }

        initPagination()
        feedPresenter.showCategories()
        initCategorySpinnerItemSelectListener()
    }

    private fun initPagination() {
        val pageListener = RecyclerViewPagination { feedPresenter.onLoadMore() }
        feed_fragment_recycler.addOnScrollListener(pageListener)
    }


    private fun initRecycle() {

        context?.let { context ->
            //            onNewsItemClickListenerFromActivity?.let {
//                feedAdapter?.addOnNewsItemClickListenerval(it)
//            }
//            feedAdapter?.addOnNewsItemClickListenerval(this)
            feedAdapter?.run {

                newsItemClickPublishObservable?.subscribeBy(

                        onNext = {
                            feedPresenter.subscribeOnNewsItem(it.id)
                        }

                )
            }

            feed_fragment_recycler.adapter = feedAdapter
            feed_fragment_recycler.addItemDecoration(ItemOffsetDecoration(context, R.dimen.short_indent))
            layoutManager = if (isVertical()) {
                GridLayoutManager(context, calculateHowManyItemsFitOnScreen())
            } else {
                LinearLayoutManager(context)
            }
            feed_fragment_recycler.layoutManager = layoutManager
        }

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
//      Отписываемся от изменения айтемов
        feedPresenter.clearTempSubscriptions()
    }


}