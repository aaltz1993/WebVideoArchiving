package blog.cmcmcmcm.webvideoarchiving.domain

import blog.cmcmcmcm.webvideoarchiving.data.database.NDatabase
import blog.cmcmcmcm.webvideoarchiving.data.database.dao.VideoDao
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Video
import blog.cmcmcmcm.webvideoarchiving.data.database.model.VideoWithTags
import blog.cmcmcmcm.webvideoarchiving.data.repository.VideoRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 */
@Singleton
class VideoRepositoryImpl @Inject constructor(
        private val database : NDatabase,
        private val videoDao : VideoDao
) : VideoRepository {

    override fun save(video: Video): Completable
            = Completable.create {
                database.runInTransaction {
                    videoDao.save(video)
                    it.onComplete()
                }
            }

    override fun getAll(): Flowable<List<VideoWithTags>> = videoDao.getAll()
}