package blog.cmcmcmcm.webvideoarchiving.ui.main.web

import android.graphics.Bitmap

/**
 *  Web view + Clients Event Listener
 */
interface WebEventListener {

    // chrome client
    fun onProgressChanged(newProgress: Int)

    // client
    fun onPageStarted(url: String, favicon: Bitmap?)

    fun onPageFinished()

    fun onLoadResource(url: String, hasVideo: Boolean)
}