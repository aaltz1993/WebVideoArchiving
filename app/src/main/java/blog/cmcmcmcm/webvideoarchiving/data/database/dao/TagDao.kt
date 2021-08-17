package blog.cmcmcmcm.webvideoarchiving.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(tag : Tag) : Long
}