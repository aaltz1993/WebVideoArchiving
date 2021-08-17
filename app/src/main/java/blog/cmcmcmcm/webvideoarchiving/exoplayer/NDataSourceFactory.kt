package blog.cmcmcmcm.webvideoarchiving.exoplayer

import android.content.Context
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject

/**
 * @Inject Module 에서 이미 그래프를 그려줬는데 필요한가요 ?
 */
class NDataSourceFactory @Inject constructor(private val context : Context,
                                     private val bandwidthMeter: BandwidthMeter) {

    /**
     * Creates DataSourceFactory
     * @param useBandwidthMeter whether use bandwidth meter or not
     */
    fun build(useBandwidthMeter : Boolean) : DataSource.Factory
            = build( bandwidthMeter.takeIf { useBandwidthMeter } )

    /* Internal
    = DefaultDataSourceFactory( context,
                                listener,
            // delegate to HttpDataSourceFactory
            DefaultHttpDataSourceFactory(Util.getUserAgent(context, applicationName),
                                        listener)
                                )
    */
    private fun build(bandwidthMeter : BandwidthMeter?) : DataSource.Factory
            = DefaultDataSourceFactory(context,
                                Util.getUserAgent(context, APPLICATION_NAME),
                                bandwidthMeter as? TransferListener<in DataSource>) // as? safe casting

    companion object {
        const val APPLICATION_NAME = "WebVideoArchiving"
    }
}