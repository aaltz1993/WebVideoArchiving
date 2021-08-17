package blog.cmcmcmcm.webvideoarchiving.data.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tag")
data class Tag (
        @PrimaryKey
        var id : Long = -1,

        var content : String = "",

        @ColumnInfo(name = "time_stamp")
        var timeStamp : Long = -1,

        @ColumnInfo(name = "video_id")
        var videoId : Long = -1
)