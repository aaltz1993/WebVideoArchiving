package blog.cmcmcmcm.webvideoarchiving.ui.main.web

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import blog.cmcmcmcm.webvideoarchiving.R
import blog.cmcmcmcm.webvideoarchiving.databinding.FragmentWebBinding
import blog.cmcmcmcm.webvideoarchiving.ui.base.BaseSupportFragment
import blog.cmcmcmcm.webvideoarchiving.ui.main.MainActivity
import blog.cmcmcmcm.webvideoarchiving.ui.main.PlayerEventListener
import blog.cmcmcmcm.webvideoarchiving.util.extension.applySettings
import blog.cmcmcmcm.webvideoarchiving.util.extension.bindClients
import blog.cmcmcmcm.webvideoarchiving.util.extension.hideKeyboard
import blog.cmcmcmcm.webvideoarchiving.util.extension.snackBar
import blog.cmcmcmcm.webvideoarchiving.util.state.DatabaseResult
import blog.cmcmcmcm.webvideoarchiving.view.webkit.NWebChromeClient
import blog.cmcmcmcm.webvideoarchiving.view.webkit.NWebViewClient
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * Web View + ExoPlayer on this Fragment
 */
class WebFragment : BaseSupportFragment(), View.OnClickListener,
        WebEventListener, OnBackKeyListener /* Web Event */ {

    private val webViewModel : WebViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(WebViewModel::class.java)
    }

    private val playerEventListener: PlayerEventListener? by lazy {
        context.takeIf { it is PlayerEventListener }
                ?.let { context as PlayerEventListener }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as MainActivity).onBackKeyListener = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_web, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentWebBinding>(view)

        // life cycle & view model
        lifecycle.addObserver(webViewModel)
        binding?.let {
            it.viewModel = webViewModel
            it.setLifecycleOwner(this@WebFragment) // web view event listener
        }

        webViewModel.databaseState.observe(this, Observer {
            when(it) {
                is DatabaseResult.Init -> {  }
                is DatabaseResult.Loading -> { /* TODO 밑에서 올라오는 로딩바 */}
                is DatabaseResult.Success -> { view.snackBar(it.message) }
                is DatabaseResult.Error -> { view.snackBar(it.throwable.message?:"Error : NONE") }
            }
        })

        initView()
    }

    private fun initView() {
        /**
         *  Web View
         */
        // clients
        webView.apply {
            bindClients(NWebViewClient(), NWebChromeClient()) // TODO Dependency
            applySettings() // custom settings
            webViewEventListener = this@WebFragment // event listener @Nullable
        }

        // start or resume
        webViewModel.url.value
                ?.takeIf { it.isNotEmpty() } // null & zero-length check
                ?.let { webView.loadUrl(it) }
                ?:run { webView.loadUrl(DEFAULT_URL) } // if null or zero-length

        /**
         *  Address Bar
         */
        // address bar
        addressBar.apply {
            //TODO
            setOnEditorActionListener { v, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_GO) {
                    Log.d("JUWONLEE","on go start")
                    v.text?.takeIf { it.isNotEmpty() } // null & zero-length check
                            ?.toString().let { webView.loadUrl(it) } // not null & length > 0
                    Log.d("JUWONLEE","on go middle")
                    v.clearFocus()
                    Log.d("JUWONLEE","on go last")
                    false
                }
                else true
            }

            setOnFocusChangeListener { _, hasFocus ->
                // control visibility state of views on address bar by xml DataBinding
                Log.d("JUWONLEE","focus : $hasFocus")
                webViewModel.setFocusOnAddressBar(hasFocus)
                Log.d("JUWONLEE","after focus : $hasFocus")
                takeIf { !hasFocus }?.hideKeyboard()
                Log.d("JUWONLEE","after focus : $hasFocus")
                return@setOnFocusChangeListener
            }
        }

        /**
         *  Others
         */
        clear.setOnClickListener(this)
        cancel.setOnClickListener(this)
        refresh.setOnClickListener(this)
        saveFloatingButton.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    // web view event
    override fun onProgressChanged(newProgress: Int) {
        webProgressBar.progress = newProgress
    }

    override fun onPageStarted(url: String, favicon: Bitmap?) {
        webProgressBar
                .takeIf { it.visibility == View.GONE }
                ?.show()

        // viewModel.address.postValue(url) < on Main Thread
        // viewModel.address.setValue(url) < on Sub Thread
        webViewModel.setUrl(url)
        webViewModel.setPlayVideo(false)
    }

    override fun onPageFinished() {
        webProgressBar
                .takeIf { it.visibility == View.VISIBLE }
                ?.hide()
    }

    override fun onLoadResource(resUrl: String, playVideo: Boolean) {
        webViewModel.setPlayVideo(playVideo)

        resUrl.takeIf { playVideo }
                ?.let {
                    webViewModel.setResUrl(it) // 이 후 유저의 저장 요청시 저장될 resource url
                    playerEventListener?.play(it)
                }
    }

    // others
    override fun onClick(v: View?) {
        when(v?.id) {
            // 주소창 비우기
            R.id.clear -> webViewModel.setUrl("")
            // 주소창 입력 또는 수정 취소
            R.id.cancel -> {
                webViewModel.setUrl(webView.url)
                addressBar.clearFocus() // TODO 안된다!
            }
            // 새로고침
            R.id.refresh -> webView.reload()
            // 비디오 저장
            R.id.saveFloatingButton -> webViewModel.save()
        }
    }

    override fun onBackPressed() : Boolean {
        if(webViewModel.playVideo.value!!) {
            webViewModel.setPlayVideo(false)
            return true
        } else if(webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }

    companion object {
        const val DEFAULT_URL = "http://www.naver.com"
    }
}
