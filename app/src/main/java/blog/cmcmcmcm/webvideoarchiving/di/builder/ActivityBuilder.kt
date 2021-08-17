//package blog.cmcmcmcm.webvideoarchiving.di.builder
//
//import blog.cmcmcmcm.webvideoarchiving.di.module.main.MainActivityModule
//import blog.cmcmcmcm.webvideoarchiving.ui.main.MainActivity
//import dagger.Module
//import dagger.android.ContributesAndroidInjector
//
///**
// *
// */
//@Module
//abstract class ActivityBuilder {
//
//
//    @ContributesAndroidInjector(modules = [
//        /* modules to install into the subcomponent */
//        MainActivityModule::class, WebFragmentBuilder::class, VideoFragmentBuilder::class
//    ])
//    abstract fun bindMainActivity() : MainActivity
//}