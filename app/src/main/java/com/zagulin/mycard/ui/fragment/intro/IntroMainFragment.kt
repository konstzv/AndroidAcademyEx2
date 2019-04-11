package com.zagulin.mycard.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zagulin.mycard.R
import com.zagulin.mycard.ui.adapters.IntroPageAdapter
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.MvpAppCompatFragment
import kotlinx.android.synthetic.main.intro_main_fragment.*


class IntroMainFragment: BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_main_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intro_fragment_view_pager   .adapter = IntroPageAdapter(
                childFragmentManager
                , arrayOf(IntroFragment.newInstance(R.drawable.feed_screen)
                , IntroFragment.newInstance(R.drawable.current_news_screen)
                , IntroFragment.newInstance(R.drawable.about_screen))
        )
//        .adapter = IntroPageAdapter(
////                supportFragmentManager
////                , arrayOf(IntroFragment.newInstance(R.drawable.feed_screen)
////                , IntroFragment.newInstance(R.drawable.current_news_screen)
////                , IntroFragment.newInstance(R.drawable.about_screen))
////        )
    }

//    @InjectPresenter
//    lateinit var introPresenter: MainPresenter
//
////    override fun moveToFeedActivity() {
////        startActivity(Intent(this, MainActivity::class.java))
////        finish()
////    }
////
//     fun showIntroActivity() {
////        setContentView(R.layout.intro_activity)
////        intro_activity_view_pager.adapter = IntroPageAdapter(
////                supportFragmentManager
////                , arrayOf(IntroFragment.newInstance(R.drawable.feed_screen)
////                , IntroFragment.newInstance(R.drawable.current_news_screen)
////                , IntroFragment.newInstance(R.drawable.about_screen))
////        )
////        intro_activity_page_indicator.setViewPager(intro_activity_view_pager)
////        intro_activity_text_view.setOnClickListener{
////            moveToFeedActivity()
////        }
//    }
}