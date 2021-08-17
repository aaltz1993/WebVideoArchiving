package blog.cmcmcmcm.webvideoarchiving.ui.main.video

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import blog.cmcmcmcm.webvideoarchiving.R
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Tag
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Video
import blog.cmcmcmcm.webvideoarchiving.util.extension.inflate
import kotlinx.android.synthetic.main.item_view_flexbox.view.*
import kotlinx.android.synthetic.main.item_view_video.view.*

class VideoViewHolder constructor(
        parent : ViewGroup
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_view_video)) {

    lateinit var url : String

    val playerView = itemView.holderPlayerView
    private val flexBox = itemView.flexBoxLayout

    fun bind(video: Video, tags : List<Tag>?) {
        Log.d("JUWONLEE", "bind : ${video.url}")
        url = video.url

        tags?.let {
            for(tag in it) {
                itemView.inflate(R.layout.item_view_flexbox)
                        .apply {
                            content.text = tag.content
                            setOnClickListener { /* player ... */ }
                        }.let { flexBox.addView(it) }
            }
        }
    }
}