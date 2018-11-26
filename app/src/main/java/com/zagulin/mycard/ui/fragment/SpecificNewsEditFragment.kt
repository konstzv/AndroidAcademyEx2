package com.zagulin.mycard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.presentation.presenter.SpecificNewsEditPresenter
import com.zagulin.mycard.presentation.view.SpecificNewsEditView
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.specific_news_edit_fragment.*
import kotlinx.android.synthetic.main.specific_news_edit_fragment_toolbar.*
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class SpecificNewsEditFragment : MvpAppCompatFragment(), SpecificNewsEditView {

    companion object {
        private const val EXTRA_NEWS_ITEM_ID = "extra_news_item_id"
        private val DATA_FORMAT = SimpleDateFormat("dd MMMM yyyy hh:mm",Locale.getDefault())

        fun newInstance(newItemId: Int): SpecificNewsEditFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_NEWS_ITEM_ID, newItemId)
            val fragment = SpecificNewsEditFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: SpecificNewsEditPresenter

    private var date:Date? = null


    override fun onCreateView(
            inflater: LayoutInflater
            , container: ViewGroup?
            , savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.specific_news_edit_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.displayNewsById(it.getInt(SpecificNewsEditFragment.EXTRA_NEWS_ITEM_ID))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    override fun displayNews(newsItem: NewsItem) {
        specific_news_edit_fragment_text_view_title.setText(newsItem.title)
        specific_news_edit_fragment_text_view_article.setText(newsItem.fullText)
        specific_news_edit_fragment_text_view_date.text = DATA_FORMAT.format(newsItem.publishDate)
    }

    private fun initListeners() {
        specific_news_activity_edit_toolbar_image_cancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        specific_news_activity_edit_toolbar_image_apply.setOnClickListener {

            presenter.saveChanges(
                    specific_news_edit_fragment_text_view_title.text.toString()
                    , specific_news_edit_fragment_text_view_article.text.toString()
                    ,DATA_FORMAT.parse(specific_news_edit_fragment_text_view_date.text.toString())

            )

        }
        specific_news_edit_fragment_text_view_date.setOnClickListener {
            activity?.let { activity ->
                val dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                        "Title example",
                        "OK",
                        "Cancel"
                )
                dateTimeDialogFragment.startAtCalendarView();
                dateTimeDialogFragment.set24HoursMode(true);
                dateTimeDialogFragment.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonClickListener {
                    override fun onPositiveButtonClick(date: Date) {
                        specific_news_edit_fragment_text_view_date.text = DATA_FORMAT.format(date)
                        // Date is get on positive button click
                        // Do something
                    }

                    override fun onNegativeButtonClick(date: Date) {
                        // Date is get on negative button click
                    }
                })

// Show

                dateTimeDialogFragment.show(activity.supportFragmentManager, "dialog_time")
            }
        }
    }

}