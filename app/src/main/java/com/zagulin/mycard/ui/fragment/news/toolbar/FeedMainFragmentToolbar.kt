package com.zagulin.mycard.ui.fragment.news.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.zagulin.mycard.R
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.HolderFragment
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.feed_main_fragment.*


class FeedMainFragmentToolbar : HolderFragment() {
//
//    companion object {
//        private const val FEED_FRAGMENT_TAG = "feed_fragment"
//        private const val SHOW_NEWS_MAIN_FRAGMENT_TAG = "feed_fragment"
//        private const val FEED_FRAGMENT_TOOLBAR_TAG = "feed_fragment_toolbar"
//    }
//
//    private var containerForFeedS: FrameLayout? = null
//    private var containerForNews: FrameLayout? = null
//
//
//
    override val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.feed_fragment_container) as? BaseFragment
//
//
//     val currentFragmentToolbar: BaseFragment?
//        get() = childFragmentManager.findFragmentById(R.id.feed_main_fragment_toolbar_container) as? BaseFragment
//
//
//    fun getOnAboutBtnClickObservable(): Observable<View>? {
//        return (childFragmentManager.findFragmentByTag(FEED_FRAGMENT_TOOLBAR_TAG)
//                as? FeedFragmentToolbar)?.getOnAboutBtnClickObservable()
//    }
//
//
//    fun showNewsItem(id: Int) {
//        showNewViewToolbar()
//        containerForNews?.let {
//            val fragment = SpecificNewsMainFragment.newInstance(id)
//
//            childFragmentManager
//                    .beginTransaction()
//                    .replace(it.id, fragment, SHOW_NEWS_MAIN_FRAGMENT_TAG)
//                    .addToBackStack(null)
//                    .commit()
//        }
//
//    }
//
//
//    fun showFeed() {
//        showFeedToolbar()
//        containerForFeedS?.let {
//            val feedFragment = FeedFragment.newInstance()
//
//
//            feedFragment.newsItemClickPublishObservable?.subscribeBy(
//                    onNext = {
//                        showNewsItem(it.id)
//                    }
//            )
//            childFragmentManager
//                    .beginTransaction()
//                    .replace(it.id, feedFragment, FEED_FRAGMENT_TAG)
//                    .addToBackStack(null)
//                    .commit()
//        }
//
//    }
//
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_main_fragment, container, false)


    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
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
//        if (savedInstanceState == null) {
//            showFeed()
//        } else {
//            val feedFragment = (childFragmentManager.findFragmentByTag(FEED_FRAGMENT_TAG) as? FeedFragment)
//
//
//            feedFragment?.newsItemClickPublishObservable?.subscribeBy(
//                    onNext = {
//                        showNewsItem(it.id)
//                    }
//            )
//        }
//
//    }
//
//    private fun showFeedToolbar() {
//        val toolbar = FeedFragmentToolbar()
//
//        childFragmentManager
//                .beginTransaction()
//                .replace(R.id.feed_main_fragment_toolbar_container, toolbar, FEED_FRAGMENT_TOOLBAR_TAG)
//                .addToBackStack("show_feed_toolbar")
//                .commit()
//    }
//
//    private fun showNewViewToolbar() {
//        val toolbar = SpecialNewsViewFragmentToolbar()
//        childFragmentManager
//                .beginTransaction()
//                .replace(R.id.feed_main_fragment_toolbar_container, toolbar, FEED_FRAGMENT_TOOLBAR_TAG)
//                .addToBackStack("show_new_view_toolbar")
//                .commit()
//    }
//
//    override fun onBackPressed() {
////
//    }


}