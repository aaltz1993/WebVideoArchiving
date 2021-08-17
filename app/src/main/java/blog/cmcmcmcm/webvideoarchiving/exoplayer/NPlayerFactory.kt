package blog.cmcmcmcm.webvideoarchiving.exoplayer

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter

/**
 * Player Instance 를 생성
 */
class NPlayerFactory {

    // not use BandwidthMeter and AdaptiveTrackSelector
    fun createSimpleExoPlayer(context : Context) : ExoPlayer = createSimpleExoPlayer(context, DefaultTrackSelector())

    // use BandwidthMeter and AdaptiveTrackSelector
    fun createSimpleExoPlayer(context : Context, bandwidthMeter: BandwidthMeter) : ExoPlayer
            = createSimpleExoPlayer(context, DefaultTrackSelector(bandwidthMeter))

    fun createSimpleExoPlayer(context : Context, trackSelector: TrackSelector) : ExoPlayer
            = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
}