package blog.cmcmcmcm.webvideoarchiving.view.exoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import blog.cmcmcmcm.webvideoarchiving.R
import blog.cmcmcmcm.webvideoarchiving.ui.main.PlayerEventListener
import blog.cmcmcmcm.webvideoarchiving.util.extension.gone
import blog.cmcmcmcm.webvideoarchiving.util.extension.visible
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlinx.android.synthetic.main.exo_player_control_view_bottom_bar.view.*

class NPlayerControlView : PlayerControlView {

    var playerEventListener: PlayerEventListener? = null

    private val enterFullscreen : View
    private val exitFullscreen : View

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // TODO do layout work
        // sync and custom

        // screen mode
        enterFullscreen = exo_enter_fullscreen
        exitFullscreen = exo_exit_fullscreen

        enterFullscreen?.setOnClickListener {
            playerEventListener?.run {
                enterFullscreen()
                it.gone()
                exitFullscreen.visible()
            }
        }
    }

    val screenModeListener = View.OnClickListener {
        when(it.id) {
            R.id.exo_enter_fullscreen -> { playerEventListener?.run {  } }
            R.id.exo_exit_fullscreen -> { }
        }
    }
}