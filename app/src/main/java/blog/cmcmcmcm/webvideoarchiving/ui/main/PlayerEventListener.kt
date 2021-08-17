package blog.cmcmcmcm.webvideoarchiving.ui.main

/**
 *  ExoPlayer View & Control View Event Listener
 */
interface PlayerEventListener {

    fun play(resUrl: String)

    fun release()

    fun enterFullscreen()

    fun exitFullscreen()
}