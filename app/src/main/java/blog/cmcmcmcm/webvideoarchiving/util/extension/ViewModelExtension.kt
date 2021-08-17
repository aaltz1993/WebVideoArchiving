package blog.cmcmcmcm.webvideoarchiving.util.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

fun <T : ViewModel> Fragment.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null) : T
    = viewModelFactory
        ?.let { ViewModelProviders.of(this, it).get(modelClass) }
        ?: ViewModelProviders.of(this).get(modelClass)

fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null) : T
        = viewModelFactory
        ?.let { ViewModelProviders.of(this, it).get(modelClass) }
        ?: ViewModelProviders.of(this).get(modelClass)