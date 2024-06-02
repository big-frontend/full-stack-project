package com.electrolytej.pisces

import android.net.http.SslError
import android.util.Log
import android.webkit.*


class H5WebViewClient : WebViewClient() {
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        Log.d("cjf","shouldInterceptRequest1:${request?.url}")
        return super.shouldInterceptRequest(view, request)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        Log.d("cjf","shouldInterceptRequest2 ${url}")
        return super.shouldInterceptRequest(view, url)
    }
    override fun onReceivedSslError(
        view: WebView,
        handler: SslErrorHandler,
        error: SslError
    ) {
        //handler.cancel(); 默认的处理方式，WebView变成空白页
        handler.proceed()
        //handleMessage(Message msg); 其他处理
    }
}