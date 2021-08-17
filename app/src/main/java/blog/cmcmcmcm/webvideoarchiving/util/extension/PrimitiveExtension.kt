package blog.cmcmcmcm.webvideoarchiving.util.extension

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

fun String.toUri() = Uri.parse(this)

fun Long.toMmSs() : String = SimpleDateFormat("mm:ss").format(Date(this))