package com.jamesfchen.h5container

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.h5container.databinding.ActivityWebviewBinding

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/24/2020  Mon
 */
class WebViewActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context,uri:String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", uri)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("url")?.let { url ->
            binding.wvContainer.loadUrl(url)
            binding.wvContainer.webViewClient = H5WebViewClient()
            binding.wvContainer.settings.javaScriptEnabled = true
            binding.wvContainer.settings.allowFileAccess = true//可以使用file:// 访问
            binding.wvContainer.settings.javaScriptCanOpenWindowsAutomatically = true
            binding.wvContainer.webChromeClient = H5WebChromeClient()
//            if (0 != (getApplicationInfo().flags and ApplicationInfo.FLAG_DEBUGGABLE)){}
            WebView.setWebContentsDebuggingEnabled(true);
//            val findSchemaRouter = Registry.getInstance().findSchemaRouter("webcontainerrouter")
//            if (findSchemaRouter !=null){
//                binding.wvContainer.addJavascriptInterface(findSchemaRouter, "ibcrouter")
//            }
//            val h5Plugin = H5Plugin()
//            wv.addJavascriptInterface(h5Plugin,h5Plugin.name)
//            binding.title.setOnClickListener {
//                binding.wvContainer.evaluateJavascript("javascript:jsAlert()") {
//                    Log.d("cjf", "jsAlert ${it}")
//                }
//            }
        }
    }
}