package blog.cmcmcmcm.webvideoarchiving.domain

import blog.cmcmcmcm.webvideoarchiving.data.database.NDatabase
import blog.cmcmcmcm.webvideoarchiving.data.database.dao.TagDao
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Tag
import blog.cmcmcmcm.webvideoarchiving.data.repository.TagRepository
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepositoryImpl @Inject constructor(
        private val database : NDatabase,
        private val tagDao: TagDao
) : TagRepository {

    override fun save(tag: Tag) : Completable
            = Completable.create {
                database.runInTransaction {
                    tagDao.save(tag = tag)
                    it.onComplete()
                }
            }
}