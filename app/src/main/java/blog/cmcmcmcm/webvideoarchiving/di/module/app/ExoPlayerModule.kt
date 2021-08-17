package blog.cmcmcmcm.webvideoarchiving.di.module.app

import android.content.Context
import blog.cmcmcmcm.webvideoarchiving.exoplayer.NDataSourceFactory
import blog.cmcmcmcm.webvideoarchiving.exoplayer.NMediaSourceFactory
import blog.cmcmcmcm.webvideoarchiving.exoplayer.NPlayerHelper
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Scope 를 사용해서 Dependency Injection per Activity
 * @PerActivity
 */
@Module
class ExoPlayerModule {

    @Provides
    @Singleton
    fun provideBandwidthMeter() : BandwidthMeter = DefaultBandwidthMeter()

    @Provides
    @Singleton
    fun provideNDataSourceFactory(context: Context, bandwidthMeter: BandwidthMeter) : NDataSourceFactory
            = NDataSourceFactory(context, bandwidthMeter)

    @Provides
    @Singleton
    @Named("data")
    fun provideDataSourceFactory(nDataSourceFactory: NDataSourceFactory) : DataSource.Factory
            = nDataSourceFactory.build(USE_BANDWIDTH_METER)
    
    @Provides
    @Singleton
    @Named("manifest")
    fun provideManifestDataSourceFactory(nDataSourceFactory: NDataSourceFactory) : DataSource.Factory
            = nDataSourceFactory.build(DISUSE_BANDWIDTH_METER)

    @Provides
    @Singleton
    fun provideNMediaSourceFactory(@Named("data") dataSourceFactory : DataSource.Factory,
                                   @Named("manifest") manifestDataSourceFactory : DataSource.Factory): NMediaSourceFactory
            = NMediaSourceFactory(dataSourceFactory, manifestDataSourceFactory)

    @Provides
    @Singleton
    fun provideDefaultTrackSelector(bandwidthMeter: BandwidthMeter) : TrackSelector
            = DefaultTrackSelector(bandwidthMeter)

    @Provides
    @Singleton
    fun provideSimpleExoPlayer(context : Context, trackSelector: TrackSelector) : ExoPlayer
            = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

    @Provides
    @Singleton
    fun provideNPlayerHelper(nMediaSourceFactory: NMediaSourceFactory, player: ExoPlayer) : NPlayerHelper
            = NPlayerHelper(nMediaSourceFactory, player)

    companion object {
        const val USE_BANDWIDTH_METER = true
        const val DISUSE_BANDWIDTH_METER = false
    }
}