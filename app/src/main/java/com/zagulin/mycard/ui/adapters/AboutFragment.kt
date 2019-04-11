package com.zagulin.mycard.ui.adapters

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import com.zagulin.mycard.R
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.zagulin.mycard.ui.activity.MvpAppCompatActivity
import com.zagulin.mycard.ui.fragment.BaseFragment
import com.zagulin.mycard.ui.fragment.MvpAppCompatFragment
import kotlinx.android.synthetic.main.about_activity.*
import kotlinx.android.synthetic.main.about_activity_bottom_sheet.*
import kotlinx.android.synthetic.main.about_activity_main_block.*


class AboutFragment : BaseFragment() {
    companion object {
        private const val MAIL_TO_URI = "mailto:"
        private const val copyringPaddingDp = 50F
    }

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.about_activity,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
        addCopyring()
        setListeners()
        setCustomToolbarIfPortrait()
    }

    private fun setBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior?.let {
            it.state = BottomSheetBehavior.STATE_HIDDEN
            it.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        fab.show()
                    } else {
                        fab.hide()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }

    }

    private fun setCustomToolbarIfPortrait() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            (activity as? MvpAppCompatActivity)?.setSupportActionBar(toolbar)
        }
    }

    private fun addCopyring() {
        val paddingPx = convertDpToPixel(copyringPaddingDp)
        val textView = TextView(context).apply {
            text = getString(R.string.copyring)
            gravity = Gravity.CENTER
            setPadding(0, paddingPx, 0, paddingPx)
        }
        main_linear_layout.addView(textView)
    }

    private fun setListeners() {

        send_msg_btn.setOnClickListener {
            onSendBtnClick()
        }
        linkedin_link.setOnClickListener {
            openUrl(getString(R.string.linkedin_url))
        }
        github_link.setOnClickListener {
            openUrl(getString(R.string.github_url))
        }
        telegram_link.setOnClickListener {
            openUrl(getString(R.string.telegram_url))
        }
        cancel_btn.setOnClickListener {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }
        fab.setOnClickListener {
            onFabCLick()
        }
        edit_text.setOnEditorActionListener { _, _, _ ->
            onSendBtnClick()
           true
        }
    }

    private fun onSendBtnClick() {
        if (sendMessageFromUser()) {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            edit_text.text.clear()
        }
    }

    private fun onFabCLick() {
        bottomSheetBehavior?.let {
            if (it.state == BottomSheetBehavior.STATE_HIDDEN) {
                it.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                it.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun openUrl(url: String) {
        val openUrlIntent = Intent(Intent.ACTION_VIEW)
        openUrlIntent.data = Uri.parse(url)
        if (checkIntentResolving(openUrlIntent)) {
            startActivity(openUrlIntent)
        } else {
            showMsgById(R.string.no_view_url_client_error)
        }
    }

    private fun sendMessageFromUser(): Boolean {
        val message = edit_text.text.toString()
        if (message.isBlank()) {
            showMsgById(R.string.edit_text_hint)
            return false
        }
        return sendMessageByEmail(message)

    }

    private fun sendMessageByEmail(message: String): Boolean {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(MAIL_TO_URI)
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.default_mail_subject))
            putExtra(Intent.EXTRA_TEXT, message)

        }
        return if (checkIntentResolving(emailIntent)) {
            startActivity(emailIntent)
            true
        } else {
            showMsgById(R.string.no_email_client_error)
            false
        }
    }

    private fun checkIntentResolving(intent: Intent): Boolean {
        activity?.run {
            if (intent.resolveActivity(packageManager) != null) {
                return true
            }
        }

        return false
    }


    private fun convertDpToPixel(dp: Float): Int {
        val px = dp * (resources.displayMetrics.densityDpi /  DisplayMetrics.DENSITY_DEFAULT)
        return Math.round(px)
    }

    private fun showMsgById(id: Int) {
        hideInput()
        activity?.run {
            Snackbar.make(
                    window.decorView.rootView,
                    id,
                    Snackbar.LENGTH_SHORT
            ).show()
        }

    }


    private fun hideInput() {
        activity?.run {
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }

}
