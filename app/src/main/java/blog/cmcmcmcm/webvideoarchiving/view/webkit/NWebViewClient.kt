package blog.cmcmcmcm.webvideoarchiving.view.webkit

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient

class NWebViewClient : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        url?.let { (view as? NestedWebView)?.onPageStarted(it, favicon) }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        (view as? NestedWebView)?.onPageFinished()
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        // url != null & none zero-length
        url?.takeIf { it.isNotEmpty() // has video
                .and( it.contains(MP4_FORMAT) || it.contains(M3U8_FORMAT) )}
                ?.let { (view as? NestedWebView)?.onLoadResource(it, true) } // trigger
    }

    companion object {
        const val MP4_FORMAT = ".mp4"
        const val M3U8_FORMAT = ".m3u8"
    }
}