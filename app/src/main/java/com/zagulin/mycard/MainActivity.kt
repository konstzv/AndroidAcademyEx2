package com.zagulin.mycard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_block.*


class MainActivity : AppCompatActivity() {
    companion object {
        private const val MAIL_TO_URI = "mailto:konstzv@gmail.com"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addCopyring()
        setListeners()

    }

    private fun addCopyring() {
        val textView = TextView(this).apply {
            text = getString(R.string.copyring)
            gravity = Gravity.CENTER
        }
        main_linear_layout.addView(textView)
    }

    private fun setListeners() {
        send_msg_btn.setOnClickListener { sendMessage(edit_text.text.toString()) }
        linkedin_link.setOnClickListener { openUrl(getString(R.string.linkedin_url)) }
        github_link.setOnClickListener { openUrl(getString(R.string.github_url)) }
        telegram_link.setOnClickListener { openUrl(getString(R.string.telegram_url)) }
    }

    private fun openUrl(url: String) {
        val openUrlIntent = Intent(Intent.ACTION_VIEW)
        openUrlIntent.data = Uri.parse(url)
        if (checkIntentResolving(openUrlIntent)) return
        Toast.makeText(this, R.string.no_view_url_client_error, Toast.LENGTH_LONG).show()
    }

    private fun sendMessage(message: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(MAIL_TO_URI)
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.default_mail_subject))
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (checkIntentResolving(emailIntent)) return
        Toast.makeText(this, R.string.no_email_client_error, Toast.LENGTH_LONG).show()

    }

    private fun checkIntentResolving(intent: Intent): Boolean {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            return true
        }
        return false
    }
}
