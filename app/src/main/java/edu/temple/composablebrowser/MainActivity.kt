package edu.temple.composablebrowser

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import edu.temple.composablebrowser.ui.theme.ComposableBrowserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableBrowserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComposableBrowserComponent(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun ComposableBrowserComponent(modifier: Modifier = Modifier){
    var urlText by remember {mutableStateOf("")}
    var webView by remember {mutableStateOf<WebView?>(null)}

    Column(modifier = modifier.fillMaxSize().padding(18.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = urlText,
                onValueChange = {urlText = it},
                label = {Text("Enter URL")},
                singleLine = true,
            )
            Button(
                onClick = {
                    if(urlText.isNotBlank()){
                        webView?.loadUrl(parseUrl(urlText))
                    }
                }
            ) {Text("GO")}
        }

        AndroidView(
            modifier = Modifier.fillMaxWidth()
                .clipToBounds()
                .weight(1f),

            factory = { context ->
                WebView(context).apply{
                    settings.javaScriptEnabled = true
                    webViewClient= WebViewClient()
                    webView = this
                }

            }
        )
    }
}

fun parseUrl(url: String ) : String  {
    if(url.startsWith("https://"))
        return url
    else
        return "https://${url}"
}