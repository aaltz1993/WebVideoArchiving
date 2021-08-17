package blog.cmcmcmcm.webvideoarchiving.di.module.app

import android.app.Application
import android.content.Context
import blog.cmcmcmcm.webvideoarchiving.NApp
import blog.cmcmcmcm.webvideoarchiving.di.module.main.MainActivityModule
import blog.cmcmcmcm.webvideoarchiving.di.scope.PerActivity
import blog.cmcmcmcm.webvideoarchiving.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(includes = [
    AndroidSupportInjectionModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    ExoPlayerModule::class,
    ViewModelModule::class
])
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun application(app : NApp): Application

    @Binds
    @Singleton
    abstract fun applicationContext(nApp : NApp) : Context

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity
}
