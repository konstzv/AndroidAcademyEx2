package com.zagulin.mycard.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zagulin.mycard.R
import kotlinx.android.synthetic.main.intro_fragment.view.*


class IntroFragment : Fragment() {

    companion object {

        private const val EXTRA_INTRO_IMG = "EXTRA_INTRO_IMG"


        fun newInstance(img: Int): IntroFragment {
            val args = Bundle()
            with(args) {

                putInt(EXTRA_INTRO_IMG, img)
            }
            val fragment = IntroFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.intro_fragment, container, false)
        arguments?.let {
            val drawableResId = it.getInt(EXTRA_INTRO_IMG)

            view.intro_fragment_image.setImageResource(drawableResId)

        }
        return view
    }

}