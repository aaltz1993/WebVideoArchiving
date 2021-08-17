//package blog.cmcmcmcm.webvideoarchiving.view
//
//import android.content.Context
//import android.support.design.widget.FloatingActionButton
//import android.support.v4.widget.ContentLoadingProgressBar
//import android.support.v7.widget.Toolbar
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.*
//import blog.cmcmcmcm.webvideoarchiving.R
//import blog.cmcmcmcm.webvideoarchiving.util.extension.gone
//import blog.cmcmcmcm.webvideoarchiving.util.extension.setAddressBarEditorAction
//import blog.cmcmcmcm.webvideoarchiving.util.extension.setFocusKeyboard
//import blog.cmcmcmcm.webvideoarchiving.ui.main.web.WebViewListener
//import blog.cmcmcmcm.webvideoarchiving.view.exoplayer.NPlayerView
//import blog.cmcmcmcm.webvideoarchiving.view.web.NWebChromeClient
//import blog.cmcmcmcm.webvideoarchiving.view.web.NWebViewClient
//import blog.cmcmcmcm.webvideoarchiving.view.web.NestedWebView
//import kotlinx.android.synthetic.main.video_nested_web_view.view.*
//
//class VideoNestedWebView : FrameLayout, View.OnClickListener {
//
//    private val mToolbar : Toolbar
//    private val mAddressBar : EditText
//    private val mClear : ImageView
//    private val mCancel : TextView
//    private val mRefresh : ImageView
//
//    private val mPlayerViewContainer : FrameLayout
//    private val mPlayerView : NPlayerView
//    private val mPlayerProgressBar: ProgressBar
//
//    private val mWebView : NestedWebView
//    private val mWebProgressBar : ContentLoadingProgressBar
//
//    private val mFAB : FloatingActionButton
//
//    private val mWebViewClient : NWebViewClient
//
//    var webViewListener : WebViewListener? = null
//
//    constructor(context: Context) : this(context, null)
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        LayoutInflater.from(context).inflate(R.layout.video_nested_web_view, this)
//
//        mToolbar = toolbar
//        mAddressBar = addressBar
//
//        mClear = clear
//        mCancel = cancel
//        mRefresh = refresh
//
//        mPlayerViewContainer = playerViewContainer
//        mPlayerView = playerView
//        mPlayerProgressBar = playerProgressBar
//
//        mWebProgressBar = webProgressBar
//
//        mFAB = floatingButton
//
//        mWebView = nestedWebView
//        mWebViewClient = NWebViewClient(context, mWebProgressBar,
//                mPlayerViewContainer, mPlayerView, mPlayerProgressBar, mAddressBar, mFAB)
//        mWebView.webViewClient = mWebViewClient
//        mWebView.webChromeClient = NWebChromeClient(mWebProgressBar, mPlayerView, mPlayerProgressBar)
//
//        mWebView.setDefaultSettings()
//
//        mAddressBar.setFocusKeyboard()
//        mAddressBar.setAddressBarEditorAction(mWebView)
//    }
//
//    fun onFocusChange(isFocused : Boolean) {
//        if(isFocused) {
//            mClear.visibility = View.VISIBLE
//            mCancel.visibility = View.VISIBLE
//
//            mRefresh.visibility = View.GONE
//        } else {
//            mClear.visibility = View.GONE
//            mCancel.visibility = View.GONE
//
//            mRefresh.visibility = View.VISIBLE
//        }
//    }
//
//    fun getWebView() : NestedWebView = mWebView
//
//    fun loadUrl(url : String) {
//        mWebView.loadUrl(url)
//    }
//
//    fun loadUrl(url : String, additionalHttpHeaders : Map<String, String>) {
//        mWebView.loadUrl(url, additionalHttpHeaders)
//    }
//
//    fun canGoBack() : Boolean = mWebView.canGoBack()
//
//    fun canGoForward() : Boolean = mWebView.canGoForward()
//
//    fun goBack() = mWebView.goBack()
//
//    fun goForward() = mWebView.goForward()
//
//    fun isPlaying() : Boolean = mPlayerViewContainer.visibility == VISIBLE
//
//    fun closeVideo() = mPlayerViewContainer.gone()
//
//
//    override fun onClick(v: View?) {
//        when(v?.id) {
//            R.id.floatingButton -> {
//                webViewListener?.let {
//                    it.save(mWebViewClient.mUrl)
//                }
//            }
//            R.id.clear -> { }
//            R.id.cancel -> {
//                mAddressBar.clearFocus()
//
//            }
//            R.id.refresh -> { mWebView.reload() }
//        }
//    }
//}