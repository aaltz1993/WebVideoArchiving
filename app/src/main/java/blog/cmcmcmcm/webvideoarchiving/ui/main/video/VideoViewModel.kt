package blog.cmcmcmcm.webvideoarchiving.ui.main.video

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import blog.cmcmcmcm.webvideoarchiving.data.repository.VideoRepository
import blog.cmcmcmcm.webvideoarchiving.data.database.model.VideoWithTags
import blog.cmcmcmcm.webvideoarchiving.util.extension.toLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * TODO view model 의 공유에 대한 부분은?
 */
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel(), LifecycleObserver {

    // Flowable < RxJava2 에 도입된 것 support back pressure TODO study
    var videoList: LiveData<List<VideoWithTags>>
            = repository.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toLiveData()

    val isPlaying: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        // do sth
    }
}