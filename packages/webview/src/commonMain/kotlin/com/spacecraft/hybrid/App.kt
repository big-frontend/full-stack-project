package com.spacecraft.hybrid

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.web.WebViewNavigator
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.multiplatform.webview.jsbridge.dataToJsonString
import com.multiplatform.webview.jsbridge.processParams
import com.spacecraft.hybrid.eventbus.FlowEventBus
import com.spacecraft.hybrid.eventbus.NavigationEvent
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.spacecraft.hybrid.page.basic.BasicScreen
import com.spacecraft.hybrid.page.html.BasicWebViewWithHTMLSample
import com.spacecraft.hybrid.page.main.MainScreen

@Composable
@Preview
fun App() {
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = "main",
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
    ) {
        composable("main") {
            MainScreen(controller)
        }
        composable("basic") {
            BasicScreen(controller)
        }
        composable("html") {
            BasicWebViewWithHTMLSample(controller)
        }
        composable("tab") {
        }
        composable("intercept") {
        }
    }
}

@Composable
fun MyContent() {
//    val navigator = rememberWebViewNavigator()
//    navigator.evaluateJavaScript("")
//    var webViewState = rememberWebViewState("https://www.geeksforgeeks.org")
//    val html = """
//    <html>
//        <body>
//            <h1>Hello World</h1>
//        </body>
//    </html>
//""".trimIndent()
//    webViewState = rememberWebViewStateWithHTMLData(
//        data = html
//    )
//    webViewState = rememberWebViewStateWithHTMLFile(
//        fileName = "index.html"
//    )
//    webViewState.webSettings.apply {
//        zoomLevel = 1.0
//        isJavaScriptEnabled = true
//        logSeverity = KLogSeverity.Debug
//        allowFileAccessFromFileURLs = true
//        allowUniversalAccessFromFileURLs = true
//        androidWebSettings.apply {
//            isAlgorithmicDarkeningAllowed = true
//            safeBrowsingEnabled = true
//            allowFileAccess = true
//        }
//    }
//    val jsBridge = rememberWebViewJsBridge()
//    jsBridge.register(GreetJsMessageHandler())

}

class GreetJsMessageHandler : IJsMessageHandler {
    override fun methodName(): String {
        return "Greet"
    }

    override fun handle(
        message: JsMessage,
        navigator: WebViewNavigator?,
        callback: (String) -> Unit,
    ) {
//        Logger.i {
//            "Greet Handler Get Message: $message"
//        }
        val param = processParams<GreetModel>(message)
        val data = GreetModel("KMM Received ${param.message}")
        callback(dataToJsonString(data))
//        EventBus.post(eventbus.NavigationEvent())
        navigator?.coroutineScope?.launch {
            FlowEventBus.publishEvent(NavigationEvent())
        }
    }
}