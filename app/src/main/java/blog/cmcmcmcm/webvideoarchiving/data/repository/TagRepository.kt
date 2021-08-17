package blog.cmcmcmcm.webvideoarchiving.data.repository

import blog.cmcmcmcm.webvideoarchiving.data.database.model.Tag
import io.reactivex.Completable

interface TagRepository {
    fun save(tag : Tag) : Completable
}