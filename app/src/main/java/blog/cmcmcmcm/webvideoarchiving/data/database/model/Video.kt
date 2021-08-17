package blog.cmcmcmcm.webvideoarchiving.data.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "nvideo")
data class Video (
        @PrimaryKey(autoGenerate = true)
        var id : Long = 0,
        var url : String = "")

