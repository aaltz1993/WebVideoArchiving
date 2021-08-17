package blog.cmcmcmcm.webvideoarchiving.data.database.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Tag
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Video

class VideoWithTags {
    @Embedded
    lateinit var video : Video

    @Relation(parentColumn = "id", entityColumn = "video_id", entity = Tag::class)
    lateinit var tags : List<Tag>
}