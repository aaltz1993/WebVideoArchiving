package blog.cmcmcmcm.webvideoarchiving.ui.base

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseSupportFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    @SuppressWarnings("deprecation")
    override fun onAttach(activity: Activity?) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    override fun onAttach(context: Context?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}