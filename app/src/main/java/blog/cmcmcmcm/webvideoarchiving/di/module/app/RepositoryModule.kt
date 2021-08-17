package blog.cmcmcmcm.webvideoarchiving.di.module.app

import blog.cmcmcmcm.webvideoarchiving.data.repository.TagRepository
import blog.cmcmcmcm.webvideoarchiving.domain.TagRepositoryImpl
import blog.cmcmcmcm.webvideoarchiving.data.repository.VideoRepository
import blog.cmcmcmcm.webvideoarchiving.domain.VideoRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * ViewModel Dependency Injection
 */
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideVideoRepository(videoRepositoryImpl: VideoRepositoryImpl) : VideoRepository
    /*
    @Provides
    fun provideVideoRepository(database: NDatabase, dao: VideoDao): VideoRepository
            = VideoRepository(database, dao)
    */

    @Binds
    abstract fun provideTagRepository(tagRepositoryImpl: TagRepositoryImpl) : TagRepository
}