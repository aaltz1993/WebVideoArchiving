package blog.cmcmcmcm.webvideoarchiving.ui.main.video

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import blog.cmcmcmcm.webvideoarchiving.data.database.model.VideoWithTags

class VideoAdapter constructor(
        private var videoList : List<VideoWithTags>
) : RecyclerView.Adapter<VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder = VideoViewHolder(parent)

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(viewHolder: VideoViewHolder, position: Int) {
        videoList[position].apply { viewHolder.bind(video, tags) }
    }

    fun submitVideoList(videoList : List<VideoWithTags>) { this.videoList = videoList }
}