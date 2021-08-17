package blog.cmcmcmcm.webvideoarchiving.exoplayer

import blog.cmcmcmcm.webvideoarchiving.util.extension.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import javax.inject.Inject


class NPlayerHelper @Inject constructor(
        private val nMediaSourceFactory: NMediaSourceFactory,
        private val mPlayer : ExoPlayer
) {

    private var mPlayingUrl : String? = null
    private var mPlayerView : PlayerView? = null

    private fun createMediaSource(url : String) : MediaSource
            = url.toUri()
                .let { nMediaSourceFactory.createMediaSource(it) }

    /**
     * Playing Video
     * @param url Video resource URL
     * @param playerView ExoPlayer View
     */
    fun play(url : String, playerView : PlayerView)
        = play(url, playerView, DEFAULT_AUTO_PLAY)

    fun play(url : String, playerView: PlayerView, autoPlay: Boolean)
        = url.takeIf { isNotSameWithPlayingUrl(it) }
            ?.let { createMediaSource(it) }
            ?.also {
                // 기존 Player View 에서 detach
                mPlayerView?.apply { player = null }
                // Play
                play(playerView, autoPlay, it)
                // Url 상태값
                mPlayingUrl = url
            }

    // url 이 여러번 호출되는 경우 play 를 막음
    private fun isNotSameWithPlayingUrl(url : String) : Boolean = (url != mPlayingUrl)

    private fun play(playerView : PlayerView, autoPlay : Boolean, mediaSource : MediaSource) {
        playerView.run {
            requestFocus()

            player = mPlayer

            mPlayer.apply {
                stop(RESET)

                playWhenReady = autoPlay
                prepare(mediaSource)
            }
        }

    }

    fun stop() {
        mPlayer.stop(RESET)

        mPlayerView = null
        mPlayingUrl = null
    }

    fun release() {
        mPlayer.release()

        mPlayerView = null
        mPlayingUrl = null
    }

    companion object {
        const val DEFAULT_AUTO_PLAY = true

        const val RESET = true
    }
}