package blog.cmcmcmcm.webvideoarchiving.ui.main

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import blog.cmcmcmcm.webvideoarchiving.R
import blog.cmcmcmcm.webvideoarchiving.databinding.ActivityMainBinding
import blog.cmcmcmcm.webvideoarchiving.exoplayer.NPlayerHelper
import blog.cmcmcmcm.webvideoarchiving.ui.base.BaseActivity
import blog.cmcmcmcm.webvideoarchiving.ui.main.video.VideoFragment
import blog.cmcmcmcm.webvideoarchiving.ui.main.web.OnBackKeyListener
import blog.cmcmcmcm.webvideoarchiving.ui.main.web.WebFragment
import blog.cmcmcmcm.webvideoarchiving.util.extension.getViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * if( SupportFragment 사용 ) : HasSupportFragmentInjector
 * else : HasFragmentInjector
 * TODO
 * 앱 시작과 동시에 video fragment 자동 재생 - dependency scope
 * 트래킹 쉽게 코드 리팩토링
 * PlayerView + Control view vieholder 에서 복제되는 문제
 * 홀더 포지션 가져올때 이상한거!
 */
class MainActivity : BaseActivity(), PlayerEventListener {

    @Inject
    lateinit var nPlayerHelper : NPlayerHelper

    // use extension
    private val mainViewModel: MainViewModel by lazy { getViewModel(MainViewModel::class.java) }

    var onBackKeyListener : OnBackKeyListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        lifecycle.addObserver(mainViewModel)
        binding.let {
            it.mainViewModel = mainViewModel
            it.setLifecycleOwner(this)
        }

        val fragmentList : List<Fragment> = listOf(WebFragment(), VideoFragment())
        initTabPager(binding.viewPager, binding.tabLayout, fragmentList)
    }

    private fun initTabPager(viewPager : ViewPager, tabLayout : TabLayout, fragmentList: List<Fragment>) {
        val mAdapter = TabPagerAdapter(supportFragmentManager, fragmentList)

        viewPager.apply {
            adapter = mAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.apply {
            addTab(newTab().setText(R.string.tab_browser))
            addTab(newTab().setText(R.string.tab_video))
        }.also {
            it.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) { viewPager.currentItem = tab?.position!! }
            })
        }
    }

    override fun onBackPressed() {
        onBackKeyListener
                ?.takeIf { viewPager.currentItem == WEB_POSITION }
                // trigger web fragment onBackPressed()
                ?.takeIf { it.onBackPressed() } // true = action consumed / false = not
                ?: super.onBackPressed()
    }

    override fun play(resUrl: String) {
        nPlayerHelper.play(resUrl, playerView)
        mainViewModel.isPlaying.value = true
    }

    override fun release() = nPlayerHelper.release()

    // full screen 관련 UI 변경은 activity 에서 처리하기 좋다
    override fun enterFullscreen() {}

    override fun exitFullscreen() {}

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        release()
        super.onDestroy()
    }

    companion object {
        const val WEB_POSITION = 0
        const val VIDEO_POSITION = 1
    }
}
