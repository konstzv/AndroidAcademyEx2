package com.zagulin.mycard.ui.fragment.news

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.zagulin.mycard.R
import com.zagulin.mycard.common.ItemOffsetDecoration
import com.zagulin.mycard.common.pagination.RecyclerViewPagination
import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.presenter.FeedPresenter
import com.zagulin.mycard.presentation.view.FeedView
import com.zagulin.mycard.ui.adapters.FeedAdapter
import com.zagulin.mycard.ui.fragment.BaseFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import kotlinx.android.synthetic.main.feed_fragment.*


class FeedFragment : BaseFragment(), FeedView {


    companion object {
        interface OnAboutButtonClickCallback {
            fun onAction()
        }
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


    @InjectPresenter
    lateinit var feedPresenter: FeedPresenter


    private var feedAdapter: FeedAdapter = FeedAdapter()
    private var layoutManager: LinearLayoutManager? = null


    override fun clearFeed() {
        feedAdapter.run {
            items = mutableListOf()
            notifyDataSetChanged()
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
        initToolbar()

    }

    override fun showProgress(isVisible: Boolean) {
        feed_fragment_progress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


    override fun addNews(list: List<FeedItem>) {

        feedAdapter.addItems(list)

    }


    override fun showMsg(msg: String) {

        Snackbar.make(
                feed_fragment_root,
                msg,
                Snackbar.LENGTH_LONG
        ).show()


    }

    private fun initToolbar() {

        initPagination()

    }

    private fun initPagination() {
        val pageListener = RecyclerViewPagination { feedPresenter.onLoadMore() }
        feed_fragment_recycler.addOnScrollListener(pageListener)
    }


    private fun initRecycle() {

        context?.let { context ->


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


    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedAdapter.run {
            compositeDisposable.add(
                    getNewsItemClickPublishObservable().subscribeBy(

                            onNext = {
                                feedPresenter.subscribeOnNewsItem(it.id)
                                feedPresenter.callOpenNews(it.id)
                            }

                    )
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onResume() {
        super.onResume()
//      Отписываемся от изменения айтемов
        feedPresenter.clearTempSubscriptions()
    }


}