package blog.cmcmcmcm.webvideoarchiving.util.extension

import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

// view
fun View.visible() { this.visibility = View.VISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun View.inflate(resId : Int) : View = LayoutInflater.from(this.context).inflate(resId, null)

// snack bar
fun View.snackBar(message : String) = Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()

// edit text
fun EditText.hideKeyboard()
        = this.windowToken.let {
            context.inputMethodManager()
                    .apply { hideSoftInputFromInputMethod(it, 0) }
        }

// web view
fun WebView.bindClients(webViewClient: WebViewClient, webChromeClient: WebChromeClient)
        = this.apply {
            this.webViewClient = webViewClient
            this.webChromeClient = webChromeClient
        }

fun WebView.applySettings()
        = this.settings
        .apply {
            javaScriptEnabled = true
            builtInZoomControls = true
            loadWithOverviewMode = true
            useWideViewPort = true
            displayZoomControls = false
            setSupportZoom(false)
            defaultTextEncodingName = "UTF-8"

            databaseEnabled = true
            domStorageEnabled = true

            cacheMode = WebSettings.LOAD_NO_CACHE
        }