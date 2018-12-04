package com.zagulin.mycard.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.presentation.presenter.SpecificNewsPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsView
import com.zagulin.mycard.ui.fragment.news.SpecificNewsEditFragment
import com.zagulin.mycard.ui.fragment.news.SpecificNewsViewFragment


class SpecificNewsActivity : MvpAppCompatActivity(), SpecificNewsView, SpecificNewsViewFragment.Companion.OnEditClickListener {

    @InjectPresenter
    lateinit var presenter: SpecificNewsPresenter

    private var newsItemId: Int? = null

    override fun showNews() {
        newsItemId?.let { newsItemId ->
            val fragment = SpecificNewsViewFragment.newInstance(newsItemId)
            fragment.onEditClickListener = this
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.specific_news_activity_content, fragment, TIME_FRAGMENT_VIEW)

                    .commit()
        }

    }

    override fun editNews() {
        newsItemId?.let { newsItemId ->
            val fragment = SpecificNewsEditFragment.newInstance(newsItemId)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.specific_news_activity_content, fragment, TIME_FRAGMENT_EDiT)

                    .commit()
        }
    }

    companion object {
        private const val TIME_FRAGMENT_VIEW = "viewFragment"
        private const val TIME_FRAGMENT_EDiT = "editFragment"
        private const val EXTRA_NEWS_ITEM_ID = "extra_news_item_id"
        fun intent(context: Context, newsItemId: Int): Intent {
            val intent = Intent(context, SpecificNewsActivity::class.java)
            intent.putExtra(EXTRA_NEWS_ITEM_ID, newsItemId)
            return intent
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_news_activity)
        newsItemId = intent.getIntExtra(EXTRA_NEWS_ITEM_ID, 0)


    }

    override fun onEditClick() {
        presenter.showEditFragment()
    }



}