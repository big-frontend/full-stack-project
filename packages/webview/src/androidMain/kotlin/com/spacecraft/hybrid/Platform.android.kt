package com.spacecraft.hybrid

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
//
//@Composable
//fun MyContent() {
//    val state = rememberWebViewState("https://www.geeksforgeeks.org")
//
//    WebView(state)
//
////    // Declare a string that contains a url
////    val mUrl = "https://www.geeksforgeeks.org"
////
////    // Adding a WebView inside AndroidView
////    // with layout as full screen
////    AndroidView(factory = {
////        WebView(it).apply {
////            layoutParams = ViewGroup.LayoutParams(
////                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT
////            )
////            webViewClient = WebViewClient()
////            loadUrl(mUrl)
////        }
////    }, update = {
////        it.loadUrl(mUrl)
////    })
//}
