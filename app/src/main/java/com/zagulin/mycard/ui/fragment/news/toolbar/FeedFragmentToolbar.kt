package com.zagulin.mycard.ui.fragment.news.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zagulin.mycard.R
import com.zagulin.mycard.models.Category
import com.zagulin.mycard.presentation.presenter.FeedFragmentToolbarPresenter
import com.zagulin.mycard.presentation.view.FeedFragmentToolbarView
import com.zagulin.mycard.ui.adapters.CategoryAdapter
import com.zagulin.mycard.ui.fragment.MvpAppCompatFragment
import kotlinx.android.synthetic.main.feed_fragment_toolbar.*


class FeedFragmentToolbar : MvpAppCompatFragment(), FeedFragmentToolbarView {

    private var categoryAdapter: CategoryAdapter? = null



    @InjectPresenter
    lateinit var presenter: FeedFragmentToolbarPresenter

    override fun setSelectedCategory(category: Category) {
        categoryAdapter?.let {
            feed_fragment_toolbar_spinner.setSelection(it.getPosition(category))
        }
    }


    override fun setCategories(categories: MutableList<Category>) {
        context?.let {
            categoryAdapter = CategoryAdapter(context = it, objects = categories)
            feed_fragment_toolbar_spinner.adapter = categoryAdapter
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment_toolbar, container, false)
    }



    private fun initCategorySpinnerItemSelectListener() {
        feed_fragment_toolbar_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoryAdapter?.getItem(position)?.let {
                    presenter.changeCategory(it)
                }

            }

        }
    }

//    var onAboutButtonClickCallback: FeedFragment.Companion.OnAboutButtonClickCallback? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feed_fragment_toolbar_image_about.setOnClickListener{
            presenter.callOpenAbout()
        }
        initCategorySpinnerItemSelectListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}