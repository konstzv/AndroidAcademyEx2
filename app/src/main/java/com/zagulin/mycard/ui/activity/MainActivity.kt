package com.zagulin.mycard.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.presentation.presenter.MainPresenter
import com.zagulin.mycard.presentation.view.MainActivityView
import com.zagulin.mycard.ui.adapters.AboutFragment
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.intro.IntroMainFragment
import com.zagulin.mycard.ui.fragment.news.FeedFragment
import com.zagulin.mycard.ui.fragment.news.FeedMainFragment


class MainActivity : MvpAppCompatActivity(), MainActivityView,FeedFragment.Companion.OnAboutButtonClickCallback {


    @InjectPresenter
    lateinit var presenter: MainPresenter


    override fun onAction() {
        showAbout()
    }

    override fun showIntro() {
        val fragment2 = IntroMainFragment()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_activity_container, fragment2, "intro_main_fragment")

                .commit()
    }


    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.main_activity_container) as? BaseFragment




    override fun showMainFeed() {

        val feedMainFragment = FeedMainFragment()

        feedMainFragment.onAboutButtonClickCallback = this
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_activity_container, feedMainFragment, "feed_main_fragment")
                .addToBackStack(null)
                .commit()

    }

    override fun showAbout() {


        val aboutFragment = AboutFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_activity_container, aboutFragment, "about_main_fragment")
                .addToBackStack(null)
                .commit()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState !=null){
            (supportFragmentManager.findFragmentByTag("feed_main_fragment")
                    as? FeedMainFragment)
                    ?.onAboutButtonClickCallback = this
        }

    }


    override fun onBackPressed() {
        currentFragment?.run {
            if (childFragmentManager.backStackEntryCount == 0) {
                if (fragmentManager?.backStackEntryCount == 0) {
                    finish()
                }else{
                    fragmentManager?.popBackStack()
                }
            } else {
                onBackPressed()
            }
        } ?:   finish()

    }
}