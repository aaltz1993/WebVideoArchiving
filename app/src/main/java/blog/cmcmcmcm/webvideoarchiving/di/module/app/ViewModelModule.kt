package blog.cmcmcmcm.webvideoarchiving.di.module.app

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import blog.cmcmcmcm.webvideoarchiving.ui.component.viewmodel.ViewModelFactory
import blog.cmcmcmcm.webvideoarchiving.ui.main.video.VideoViewModel
import blog.cmcmcmcm.webvideoarchiving.ui.main.web.WebViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WebViewModel::class)
    abstract fun bindWebViewModel(webViewModel: WebViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    abstract fun bindVideoViewModel(videoViewModel: VideoViewModel): ViewModel
}