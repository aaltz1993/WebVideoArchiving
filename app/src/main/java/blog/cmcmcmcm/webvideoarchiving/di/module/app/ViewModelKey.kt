package blog.cmcmcmcm.webvideoarchiving.di.module.app

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

// TODO ?
@Target(allowedTargets = [
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
])
@Retention(value = AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey (val value: KClass<out ViewModel>) // KClass ::class