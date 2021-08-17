package blog.cmcmcmcm.webvideoarchiving.view.webkit

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView

class NWebChromeClient : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        (view as NestedWebView).onProgressChanged(newProgress)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
    }

    override fun getVideoLoadingProgressView(): View {
        return super.getVideoLoadingProgressView()
    }
}