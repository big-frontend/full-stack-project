package com.electrolytej.pisces

import android.util.Log
import android.webkit.JavascriptInterface

class H5Plugin {
    val name: String
    get() = "pay"

    @JavascriptInterface
    fun say(): String {
        Log.d("cjf", " native say calling from js")
        return "sb"
    }
}