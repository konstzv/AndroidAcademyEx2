package com.zagulin.mycard.ui.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zagulin.mycard.R
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.HolderFragment
import io.reactivex.rxkotlin.subscribeBy


class FeedMainFragment: HolderFragment(){

    companion object{
        private const val FEED_FRAGMENT_TAG = "feed_fragment"
        private const val SHOW_NEWS_MAIN_FRAGMENT_TAG = "feed_fragment"
    }




    var onAboutButtonClickCallback:FeedFragment.Companion.OnAboutButtonClickCallback? = null

     override val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.feed_fragment_container) as? BaseFragment


     fun showNewsItem(id: Int) {
        val fragment = SpecificNewsMainFragment.newInstance(id)

        childFragmentManager
                .beginTransaction()
                .replace(R.id.feed_fragment_container, fragment, SHOW_NEWS_MAIN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()
    }


     fun showFeed() {
          val feedFragment = FeedFragment.newInstance()

        feedFragment.onAboutButtonClickCallback = onAboutButtonClickCallback
        feedFragment.newsItemClickPublishObservable?.subscribeBy(
                onNext = {
                    showNewsItem(it.id)
                }
        )
         childFragmentManager
                .beginTransaction()
                .replace(R.id.feed_fragment_container, feedFragment, FEED_FRAGMENT_TAG)
                 .addToBackStack(null)
                .commit()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.feed_main_fragment,container,false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null){
            showFeed()
        }else{
            val feedFragment = (childFragmentManager.findFragmentByTag(FEED_FRAGMENT_TAG) as? FeedFragment)
            feedFragment ?.onAboutButtonClickCallback = onAboutButtonClickCallback

            feedFragment?.newsItemClickPublishObservable?.subscribeBy(
                    onNext = {
                        showNewsItem(it.id)
                    }
            )
        }

        }







}