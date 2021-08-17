package blog.cmcmcmcm.webvideoarchiving.ui.main

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel(), LifecycleObserver {

    val isPlaying : MutableLiveData<Boolean> = MutableLiveData()

    init {
        isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}