package blog.cmcmcmcm.webvideoarchiving.exoplayer

import com.google.android.exoplayer2.ui.PlayerView

interface NPlayer {

    fun play(url : String, playerView : PlayerView)

    fun play(url : String, playerView: PlayerView, autoPlay: Boolean)

    fun stop()

    fun release()
}