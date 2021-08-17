package blog.cmcmcmcm.webvideoarchiving.view.exoplayer

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.PlayerView

class NPlayerView : PlayerView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }


}