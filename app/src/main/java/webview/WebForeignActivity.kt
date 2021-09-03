package webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.android.newsupdate.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_web_foreign.*

class WebForeignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_foreign)

        val url = intent.getStringExtra("URL")
        if(url != null){
            foreignWebView.settings.javaScriptEnabled = true //if webview contain any java script related work then would be visible
            foreignWebView.settings.userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3" //to make sure mobile webview should be send
            foreignWebView.webViewClient = object: WebViewClient(){  //Getting the webView and storing in the object
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)  //After successful rendering it will display the data
                    foreignProgressBar.visibility = View.GONE
                    foreignWebView.visibility = View.VISIBLE
                }
            }
            foreignWebView.loadUrl(url)
        }
    }
}