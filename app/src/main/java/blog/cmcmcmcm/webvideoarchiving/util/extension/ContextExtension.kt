package blog.cmcmcmcm.webvideoarchiving.util.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Context.inputMethodManager() = (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)