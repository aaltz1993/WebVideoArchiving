package blog.cmcmcmcm.webvideoarchiving.ui.main.video

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blog.cmcmcmcm.webvideoarchiving.R
import blog.cmcmcmcm.webvideoarchiving.data.database.model.Video
import blog.cmcmcmcm.webvideoarchiving.data.database.model.VideoWithTags
import blog.cmcmcmcm.webvideoarchiving.databinding.FragmentVideoBinding
import blog.cmcmcmcm.webvideoarchiving.exoplayer.NPlayerHelper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_video.*
import javax.inject.Inject

/**
 * Video List Fragment
 */
class VideoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: VideoViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel::class.java)
    }

    @Inject
    lateinit var nPlayerHelper: NPlayerHelper

    // TEST
    private val vwtList : List<VideoWithTags>

    init {
        val vwt = VideoWithTags()
        vwt.apply {
            video = Video(0, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            tags = emptyList()
        }

        val vwt1 = VideoWithTags()
        vwt1.apply {
            video = Video(1, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
            tags = emptyList()
        }


        val vwt2 = VideoWithTags()
        vwt2.apply {
            video = Video(2, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
            tags = emptyList()
        }

        val vwt3 = VideoWithTags()
        vwt3.apply {
            video = Video(3, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
            tags = emptyList()
        }

        vwtList = listOf(vwt, vwt1, vwt2, vwt3)
    }

    @SuppressWarnings("deprecation")
    override fun onAttach(activity: Activity?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(activity)
    }

    override fun onAttach(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentVideoBinding>(view)

        // life cycle & view model
        lifecycle.addObserver(viewModel)
        binding?.also {
            it.viewModel = viewModel
            it.setLifecycleOwner(this@VideoFragment) // readable
        }

        // recycler view
        initRecyclerView(recyclerView)

        /*
        viewModel.videoList.observe(this, Observer {
            (recyclerView.adapter as VideoAdapter).apply {
                submitVideoList(it!!)
                // TODO DiffUtil
                notifyDataSetChanged()
            }
        })
        */
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = VideoAdapter(vwtList)
            layoutManager = LinearLayoutManager(context)
        }.apply {
            // 스크롤에 따른 동영상 재생
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView?.layoutManager as LinearLayoutManager)
                            .apply {
                                // 재생
                                findFirstCompletelyVisibleItemPosition()
                                        .takeIf { it != RecyclerView.NO_POSITION && isVisibleToUser }
                                        // TODO position 이 생각한 것과 다르다
                                        ?.let { (findViewHolderForLayoutPosition(it) as VideoViewHolder)
                                                .run { nPlayerHelper.play(url, playerView, true) }
                                        }
                            }
                }
            })
        }
    }

    var isVisibleToUser: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        this.isVisibleToUser = isVisibleToUser

        recyclerView?.apply {
            (layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
                    .takeIf { it != RecyclerView.NO_POSITION }
                    ?.let {
                        (findViewHolderForAdapterPosition(it) as VideoViewHolder).run {
                            if (isVisibleToUser) nPlayerHelper.play(url, playerView)
                            else nPlayerHelper.stop()
                        }
                    }
        }
    }

    override fun onPause() {
        nPlayerHelper.stop()
        super.onPause()
    }
}