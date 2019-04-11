package com.zagulin.mycard.ui.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.presentation.presenter.FeedMainPresenter
import com.zagulin.mycard.presentation.view.FeedMainFragmentView
import com.zagulin.mycard.presentation.view.SpecialNewsFragmentToolbarView
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.HolderFragment
import com.zagulin.mycard.ui.fragment.news.toolbar.FeedFragmentToolbar
import com.zagulin.mycard.ui.fragment.news.toolbar.SpecialNewsEditFragmentToolbar
import com.zagulin.mycard.ui.fragment.news.toolbar.SpecialNewsViewFragmentToolbar
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.feed_main_fragment.*


class FeedMainFragment : HolderFragment(), FeedMainFragmentView {




    companion object {
        private const val FEED_FRAGMENT_TAG = "feed_fragment"
        private const val SHOW_NEWS_MAIN_FRAGMENT_TAG = "show_news_fragment"
        private const val EDIT_NEWS_MAIN_FRAGMENT_TAG = "edit_news_fragment"
        private const val FEED_FRAGMENT_TOOLBAR_TAG = "feed_fragment_toolbar"
        private const val SHOW_NEWS_MAIN_FRAGMENT_TOOLBAR_TAG = "show_news_fragment_toolbar"
        private const val EDIT_NEWS_MAIN_FRAGMENT_TOOLBAR_TAG = "edit_news_fragment_toolbar"
    }

    private var containerForFeedS: FrameLayout? = null
    private var containerForNews: FrameLayout? = null

    @InjectPresenter
    lateinit var presenter: FeedMainPresenter


    override val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.feed_fragment_container) as? BaseFragment


    override fun showEdit(id: Int) {
//        containerForFeedS?.let {
        val fragment = SpecificNewsEditFragment.newInstance(id)
        val toolbar = SpecialNewsEditFragmentToolbar()
        childFragmentManager
                .beginTransaction()
                .replace(R.id.feed_fragment_container, fragment, EDIT_NEWS_MAIN_FRAGMENT_TAG)
                .replace(R.id.feed_main_fragment_toolbar_container, toolbar, EDIT_NEWS_MAIN_FRAGMENT_TOOLBAR_TAG)
                .addToBackStack("show_edit")
                .commit()
//        }

    }


    override fun showNews(id: Int) {
//        containerForFeedS?.let {
            val feedFragment = SpecificNewsViewFragment.newInstance(id)
            val toolbar = SpecialNewsViewFragmentToolbar()


            childFragmentManager
                    .beginTransaction()
                    .replace(R.id.feed_fragment_container, feedFragment, SHOW_NEWS_MAIN_FRAGMENT_TAG)
                    .replace(R.id.feed_main_fragment_toolbar_container, toolbar, SHOW_NEWS_MAIN_FRAGMENT_TOOLBAR_TAG)
                    .addToBackStack(null)
                    .commit()
        }





    override fun showFeed() {
//        showFeedToolbar()
//        containerForFeedS?.let {
            val feedFragment = FeedFragment()
            val toolbar = FeedFragmentToolbar()

            childFragmentManager
                    .beginTransaction()
                    .replace(R.id.feed_fragment_container, feedFragment, FEED_FRAGMENT_TAG)
                    .replace(R.id.feed_main_fragment_toolbar_container, toolbar, FEED_FRAGMENT_TOOLBAR_TAG)

                    .commit()
//        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_main_fragment, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Toast.makeText(activity,isTwoPanelMode().toString(),Toast.LENGTH_LONG).show()
        if (isTwoPanelMode()){

        }
//        showFeedToolbar()
//
//
//        feed_fragment_container?.let {
//            containerForFeedS = feed_fragment_container
//            containerForNews = feed_fragment_container
//        } ?: run {
//            containerForFeedS = feed_fragment_container_left
//            containerForNews = feed_fragment_container_right
//        }


    }

    private fun isTwoPanelMode():Boolean{
        return feed_fragment_container == null
    }


}