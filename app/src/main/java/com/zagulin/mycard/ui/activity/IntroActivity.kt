package com.zagulin.mycard.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.presentation.presenter.FeedPresenter
import com.zagulin.mycard.presentation.presenter.IntroPresenter
import com.zagulin.mycard.presentation.view.IntroView
import com.zagulin.mycard.ui.adapters.IntroPageAdapter
import kotlinx.android.synthetic.main.intro_activity.*


class IntroActivity:MvpAppCompatActivity(),IntroView{


    @InjectPresenter
    lateinit var introPresenter: IntroPresenter

    override fun moveToFeedActivity() {
        startActivity(Intent(this, FeedActivity::class.java))
        finish()
    }

    override fun showIntroActivity() {
        setContentView(R.layout.intro_activity)
        intro_activity_view_pager.adapter = IntroPageAdapter(
                supportFragmentManager
                , arrayOf(IntroFragment.newInstance(R.drawable.feed_screen)
                ,IntroFragment.newInstance(R.drawable.current_news_screen)
                ,IntroFragment.newInstance(R.drawable.about_screen))
        )
        intro_activity_page_indicator.setViewPager(intro_activity_view_pager)
        intro_activity_text_view.setOnClickListener{
            moveToFeedActivity()
        }
    }



}