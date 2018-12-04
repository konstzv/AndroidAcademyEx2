package com.zagulin.mycard.ui.fragment.news



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zagulin.mycard.R
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.HolderFragment
import com.zagulin.mycard.ui.fragment.MvpAppCompatFragment

class SpecificNewsMainFragment: HolderFragment(), SpecificNewsViewFragment.Companion.OnEditClickListener {


    override val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.specific_news_main_fragment_container) as? BaseFragment
    var id:Int? = null

    override fun onEditClick() {
        id?.let {
            editItem(it)
        }
    }

    companion object {
        private const val EXTRA_NEWS_ITEM_ID = "extra_news_item_id"

        fun newInstance(newItemId: Int): SpecificNewsMainFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_NEWS_ITEM_ID, newItemId)
            val fragment = SpecificNewsMainFragment()
            fragment.arguments = args
            return fragment
        }
    }

     fun displayItem(id: Int) {

        val fragment = SpecificNewsViewFragment.newInstance(id)
        fragment.onEditClickListener = this
        childFragmentManager.run {
            beginTransaction()
                    .replace(R.id.specific_news_main_fragment_container, fragment, "test")

                    .commit()

        }

    }

    fun editItem(id: Int) {

        val fragment = SpecificNewsEditFragment.newInstance(id)

        childFragmentManager.run {
            beginTransaction()
                    .replace(R.id.specific_news_main_fragment_container, fragment, "test")
                    .addToBackStack(null)
                    .commit()

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.specific_news_main_fragment,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id =arguments?.getInt(EXTRA_NEWS_ITEM_ID)
        if (savedInstanceState==null){
            id?.let {
                displayItem(it)
            }
//
        }

    }




}