package blog.cmcmcmcm.webvideoarchiving.util.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resId : Int) : View = LayoutInflater.from(this.context).inflate(resId, this, false)

