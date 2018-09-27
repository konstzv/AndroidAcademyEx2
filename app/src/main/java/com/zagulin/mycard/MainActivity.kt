package com.zagulin.mycard

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_block.*


class MainActivity : AppCompatActivity() {
    companion object {
        private const val MAIL_TO_URI = "mailto:konstzv@gmail.com"
        private const val copyringPaddingDp = 50F
    }

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottomSheet()
        addCopyring()
        setListeners()
        setCustomToolbarIfPortrait()

    }

    private fun setBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetCallback() {
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

    private fun setCustomToolbarIfPortrait() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setSupportActionBar(toolbar)

        }
    }

    private fun addCopyring() {
        val paddingPx = convertDpToPixel(copyringPaddingDp)
        val textView = TextView(this).apply {
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
        linkedin_link.setOnClickListener { openUrl(getString(R.string.linkedin_url)) }
        github_link.setOnClickListener { openUrl(getString(R.string.github_url)) }
        telegram_link.setOnClickListener { openUrl(getString(R.string.telegram_url)) }
        cancel_btn.setOnClickListener { bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN }
        fab.setOnClickListener { onFabCLick() }

        edit_text.setOnEditorActionListener { _, _, _ ->
            onSendBtnClick()
            return@setOnEditorActionListener true
        }
    }

    private fun onSendBtnClick() {
        if (sendMessageFromUser()) {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            edit_text.setText("")
        }
    }

    private fun onFabCLick() {

        if (bottomSheetBehavior != null) {
            if (bottomSheetBehavior!!.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED

            } else {
                bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN

            }
        }
    }

    private fun openUrl(url: String) {
        val openUrlIntent = Intent(Intent.ACTION_VIEW)
        openUrlIntent.data = Uri.parse(url)
        if (checkIntentResolving(openUrlIntent)) {
            startActivity(openUrlIntent)
        } else {
            Toast.makeText(this, R.string.no_view_url_client_error, Toast.LENGTH_LONG).show()
        }
    }

    private fun sendMessageFromUser(): Boolean {
        val message = edit_text.text.toString()
        if (message.isBlank()) {
            Toast.makeText(this, R.string.edit_text_hint, Toast.LENGTH_LONG).show()
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
            Toast.makeText(this, R.string.no_email_client_error, Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun checkIntentResolving(intent: Intent): Boolean {
        if (intent.resolveActivity(packageManager) != null) {
            return true
        }
        return false
    }


    fun convertDpToPixel(dp: Float): Int {
        val px = dp * (resources.displayMetrics.densityDpi / 160f)
        return Math.round(px)
    }

}
