package blog.cmcmcmcm.webvideoarchiving.di.module.app

import android.arch.persistence.room.Room
import blog.cmcmcmcm.webvideoarchiving.NApp
import blog.cmcmcmcm.webvideoarchiving.data.database.NDatabase
import blog.cmcmcmcm.webvideoarchiving.data.database.dao.TagDao
import blog.cmcmcmcm.webvideoarchiving.data.database.dao.VideoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(NApp : NApp) : NDatabase
            = Room.databaseBuilder(NApp, NDatabase::class.java, "nhack.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideVideoDao(database : NDatabase) : VideoDao = database.videoDao

    @Provides
    @Singleton
    fun provideTagDao(database : NDatabase) : TagDao = database.tagDao
}