package com.doubleslas.fifith.alcohol.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.databinding.ActivityWebviewBinding
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity


class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding

    private val url by lazy { intent.getStringExtra(URL)!! }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = WebViewClient() // 클릭시 새창 안뜨게

        binding.webView.settings.apply {
            setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
            javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
            loadWithOverviewMode = true // 메타태그 허용 여부
            useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
            setSupportZoom(false) // 화면 줌 허용 여부
            builtInZoomControls = false // 화면 확대 축소 허용 여부
            cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
            domStorageEnabled = true // 로컬저장소 허용 여부
        }

        binding.webView.loadUrl(url) // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }


    companion object {
        private const val URL = "URL"
        fun getStartIntent(context: Context, url: String): Intent {

            return Intent(context, WebActivity::class.java).apply {
                putExtra(URL, url)
            }
        }
    }

}