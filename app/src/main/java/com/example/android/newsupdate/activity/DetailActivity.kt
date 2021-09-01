package com.example.android.newsupdate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.android.newsupdate.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.progress_dialog.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val url = intent.getStringExtra("URL")
        if(url != null){
            detailwebView.settings.javaScriptEnabled = true
            detailwebView.settings.userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
            detailwebView.webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    detailprogressBar.visibility = View.GONE
                    detailwebView.visibility = View.VISIBLE
                }
            }
            detailwebView.loadUrl(url)
        }
    }
}