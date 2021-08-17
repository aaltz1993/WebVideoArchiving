package blog.cmcmcmcm.webvideoarchiving.ui.component.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * @JvmSuppressWildcards
 *
 * Error:[dagger.android.AndroidInjector.inject(T)]
 * java.util.Map<java.lang.Class<? extends android.arch.lifecycle.ViewModel>,
 * ? extends javax.inject.Provider<android.arch.lifecycle.ViewModel>> cannot be provided without an @Provides-annotated method.
 */
class ViewModelFactory @Inject constructor (
    private val creators: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass as Class<ViewModel>]
                ?: creators.entries.firstOrNull{ (c, _) -> modelClass.isAssignableFrom(c)}?.value
                ?: throw IllegalArgumentException("Unknown model class $modelClass")

        return creator.get() as T
    }
}