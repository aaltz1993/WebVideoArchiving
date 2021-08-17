package blog.cmcmcmcm.webvideoarchiving.ui.main.web

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Video
import blog.cmcmcmcm.webvideoarchiving.data.repository.VideoRepository
import blog.cmcmcmcm.webvideoarchiving.util.state.DatabaseResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *
 */
class WebViewModel @Inject constructor(
        private val repository: VideoRepository
) : ViewModel(), LifecycleObserver {

    // address bar
    // focus ( xml data binding )
    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> // getter
        get() = _url

    fun setUrl(url : String) { // setter
        _url.takeIf { it.value != url }
                ?.let { it.value = url }
    }

    private val _hasFocusOnAddressBar: MutableLiveData<Boolean> = MutableLiveData()
    val hasFocusOnAddressBar: LiveData<Boolean>
        get() = _hasFocusOnAddressBar

    fun setFocusOnAddressBar(hasFocus : Boolean) {
        _hasFocusOnAddressBar
                .takeIf { it.value != hasFocus }
                ?.let { it.value = hasFocus }
    }

    // video
    private val _playVideo: MutableLiveData<Boolean> = MutableLiveData()
    val playVideo : LiveData<Boolean>
        get() = _playVideo

    fun setPlayVideo(playVideo : Boolean)
        = _playVideo
                .takeIf { it.value != playVideo }
                ?.let { it.value = playVideo }


    private var resUrl : MutableLiveData<String> = MutableLiveData()

    fun setResUrl(url : String)
        = resUrl.takeIf { it.value != url }
                ?.let { it.value = url }

    // database
    var databaseState : MutableLiveData<DatabaseResult<String>> = MutableLiveData()

    fun save() {
        resUrl.value
                ?.takeIf { it.isNotEmpty() }
                ?.let { Video(0, it) } // TODO
                ?.also { save(it) }
                ?:hasNoVideoError()
    }

    private fun hasNoVideoError() {
        // snack bar trigger
        databaseState.value = DatabaseResult.Error(Throwable("No video or already saved video"))
    }

    private fun save(video : Video)
            = repository.save(video)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { databaseState.value = DatabaseResult.Init() } // Init
            .doOnComplete { databaseState.value = DatabaseResult.Loading()  } // Loading
            .subscribe({ // Success
                databaseState.value = DatabaseResult.Success("동영상을 저장했습니다")
                resUrl = MutableLiveData()
            }, { // Error
                databaseState.value = DatabaseResult.Error(it)
            })

    override fun onCleared() {
        super.onCleared()
    }
}