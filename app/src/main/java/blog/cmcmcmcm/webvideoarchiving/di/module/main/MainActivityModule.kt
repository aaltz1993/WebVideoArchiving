package blog.cmcmcmcm.webvideoarchiving.di.module.main

import android.content.Context
import blog.cmcmcmcm.webvideoarchiving.di.scope.PerActivity
import blog.cmcmcmcm.webvideoarchiving.di.scope.PerFragment
import blog.cmcmcmcm.webvideoarchiving.ui.main.MainActivity
import blog.cmcmcmcm.webvideoarchiving.ui.main.video.VideoFragment
import blog.cmcmcmcm.webvideoarchiving.ui.main.web.WebFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @Binds
    @PerActivity
    abstract fun activityContext(mainActivity: MainActivity): Context

    @PerFragment
    @ContributesAndroidInjector(modules = [/*WebFragmentModule::class*/])
    abstract fun webFragmentInjector(): WebFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [/*VideoFragmentModule::class*/])
    abstract fun videoFragmentInjector(): VideoFragment
}