package com.zagulin.mycard.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zagulin.mycard.R
import com.zagulin.mycard.ui.fragment.SpecificNewsEditFragment
import com.zagulin.mycard.ui.fragment.SpecificNewsViewFragment


class SpecificNewsActivity : AppCompatActivity(), SpecificNewsViewFragment.Companion.OnEditClickListener {
    companion object {
        private const val EXTRA_NEWS_ITEM_ID = "extra_news_item_id"
        fun intent(context: Context, newsItemId: Int): Intent {
            val intent = Intent(context, SpecificNewsActivity::class.java)
            intent.putExtra(EXTRA_NEWS_ITEM_ID, newsItemId)
            return intent
        }

    }


    private var newsItemId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_news_activity)
        newsItemId = intent.getIntExtra(EXTRA_NEWS_ITEM_ID, 0)
        showViewFragment(newsItemId)


    }

    override fun onEditClick() {
        shoEditFragment(newsItemId)
    }

    private fun showViewFragment(newsItemId: Int) {
        val fragment = SpecificNewsViewFragment.newInstance(newsItemId)
        fragment.onEditClickListener = this
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.specific_news_activity_content, fragment, "viewFragment")

                .commit()
    }

    private fun shoEditFragment(newsItemId: Int) {
        val fragment = SpecificNewsEditFragment.newInstance(newsItemId)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.specific_news_activity_content, fragment, "editFragment")
                .addToBackStack(null)
                .commit()
    }

}