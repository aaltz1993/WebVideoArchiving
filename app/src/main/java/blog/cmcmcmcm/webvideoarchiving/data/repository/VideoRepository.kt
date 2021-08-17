package blog.cmcmcmcm.webvideoarchiving.data.repository

import blog.cmcmcmcm.webvideoarchiving.data.database.model.Video
import blog.cmcmcmcm.webvideoarchiving.data.database.model.VideoWithTags
import io.reactivex.Completable
import io.reactivex.Flowable

interface VideoRepository {
    fun save(video : Video) : Completable

    fun getAll() : Flowable<List<VideoWithTags>>
}