package blog.cmcmcmcm.webvideoarchiving.exoplayer

import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject

class NMediaSourceFactory @Inject constructor(
        private val dataSourceFactory: DataSource.Factory,
        private val manifestDataSourceFactory: DataSource.Factory){

    fun createMediaSource(uri: Uri) : MediaSource {
        val type = Util.inferContentType(uri)
        return createMediaSource(uri, type)
    }

    /**
     * Create Media Source by type
     *
     * @param uri video uri
     * @param type video content type
     */
    private fun createMediaSource(uri: Uri, @C.ContentType type: Int) : MediaSource
        = when(type) {
            C.TYPE_SS -> SsMediaSource
                    // ChunkSource Factory, ManifestDataSourceFactory(no need for bandwidth meter)
                    .Factory(DefaultSsChunkSource.Factory(dataSourceFactory), manifestDataSourceFactory)
                    .createMediaSource(uri)
            C.TYPE_DASH -> DashMediaSource
                    // ChunkSource Factory, ManifestDataSourceFactory(no need for bandwidth meter)
                    .Factory(DefaultDashChunkSource.Factory(dataSourceFactory), manifestDataSourceFactory)
                    .createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource
                    .Factory(dataSourceFactory) // Internally DefaultHlsDataSourceFactory(dataSourceFactory)
                    .createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(uri)
            else -> throw IllegalArgumentException("Unknown type !")
        }
}