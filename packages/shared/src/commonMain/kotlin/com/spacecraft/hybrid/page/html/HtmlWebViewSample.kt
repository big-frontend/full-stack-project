package com.spacecraft.hybrid.page.html

import com.spacecraft.hybrid.GreetJsMessageHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.util.KLogSeverity
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLFile
import com.spacecraft.hybrid.eventbus.FlowEventBus
import com.spacecraft.hybrid.eventbus.NavigationEvent
import kotlinx.coroutines.flow.filter


@Composable
internal fun BasicWebViewWithHTMLSample(navHostController: NavHostController? = null) {
    var webViewState =
        rememberWebViewStateWithHTMLFile(
            fileName = "index.html",
        )
    webViewState = rememberWebViewStateWithHTMLData(html)
    val webViewNavigator = rememberWebViewNavigator()
    val jsBridge = rememberWebViewJsBridge(webViewNavigator)
    var jsRes by mutableStateOf("Evaluate JavaScript")
    LaunchedEffect(Unit) {
        initWebView(webViewState)
        initJsBridge(jsBridge)
    }
    MaterialTheme {
        Column {
            TopAppBar(
                title = { Text(text = "Html Sample") },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController?.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )

            Box(Modifier.fillMaxSize()) {
                WebView(
                    state = webViewState,
                    modifier = Modifier.fillMaxSize(),
                    captureBackPresses = false,
                    navigator = webViewNavigator,
                    webViewJsBridge = jsBridge,
                )
                Button(
                    onClick = {
                        webViewNavigator.evaluateJavaScript(
                            """
                            document.getElementById("subtitle").innerText = "Hello from KMM!";
                            window.kmpJsBridge.callNative("Greet",JSON.stringify({message: "Hello"}),
                                function (data) {
                                    document.getElementById("subtitle").innerText = data;
                                    console.log("Greet from Native: " + data);
                                }
                            );
                            callJS();
                            """.trimIndent(),
                        ) {
                            jsRes = it
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 50.dp),
                ) {
                    Text(jsRes)
                }
            }
        }
    }
}

fun initWebView(webViewState: WebViewState) {
    webViewState.webSettings.apply {
        zoomLevel = 1.0
        isJavaScriptEnabled = true
        logSeverity = KLogSeverity.Debug
        allowFileAccessFromFileURLs = true
        allowUniversalAccessFromFileURLs = true
        androidWebSettings.apply {
            isAlgorithmicDarkeningAllowed = true
            safeBrowsingEnabled = true
            allowFileAccess = true
        }
    }
}

suspend fun initJsBridge(webViewJsBridge: WebViewJsBridge) {
    webViewJsBridge.register(GreetJsMessageHandler())
    //        EventBus.observe<eventbus.NavigationEvent> {
//            Logger.d {
//                "Received eventbus.NavigationEvent"
//            }
//        }
    FlowEventBus.events.filter { it is NavigationEvent }.collect {
//        Logger.d {
//            "Received eventbus.NavigationEvent"
//        }
    }
}
