package blog.cmcmcmcm.webvideoarchiving.di.component

import blog.cmcmcmcm.webvideoarchiving.NApp
import blog.cmcmcmcm.webvideoarchiving.di.module.app.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<NApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<NApp>()
}
