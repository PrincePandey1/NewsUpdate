package webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.android.newsupdate.R
import kotlinx.android.synthetic.main.activity_web_business.*
import kotlinx.android.synthetic.main.activity_web_business.busWebView
import kotlinx.android.synthetic.main.activity_web_media.*

class WebMediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_media)

        val url = intent.getStringExtra("URL")
        if (url != null) {
            mediaWebView.settings.javaScriptEnabled = true
            mediaWebView.settings.userAgentString =
                "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
            mediaWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    mediaWebView.visibility = View.VISIBLE
                    mediaProgressBar.visibility = View.GONE
                }
            }
            mediaWebView.loadUrl(url)
        }
    }
}