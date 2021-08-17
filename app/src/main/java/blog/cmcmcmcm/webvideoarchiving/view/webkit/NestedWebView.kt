package blog.cmcmcmcm.webvideoarchiving.view.webkit

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.view.NestedScrollingChild
import android.support.v4.view.NestedScrollingChildHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import blog.cmcmcmcm.webvideoarchiving.ui.main.web.WebEventListener

class NestedWebView : WebView, NestedScrollingChild {

    // webview event listener
    var webViewEventListener : WebEventListener? = null

    // behavior
    private var mLastY = 0f
    private var mScrollOffset : IntArray = IntArray(2)
    private var mScrollConsumed : IntArray = IntArray(2)
    private var mNestedOffsetY : Float = 0f

    private val helper : NestedScrollingChildHelper by lazy { NestedScrollingChildHelper(this) }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, android.R.attr.webViewStyle)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        isNestedScrollingEnabled = true
    }

    // chrome client
    fun onProgressChanged(newProgress: Int) {
        webViewEventListener?.onProgressChanged(newProgress)
    }

    // client
    fun onPageStarted(url: String, favicon: Bitmap? /* 당장 사용 X */) {
        webViewEventListener?.onPageStarted(url, favicon)
    }

    fun onPageFinished() = webViewEventListener?.onPageFinished()

    fun onLoadResource(url: String, hasVideo: Boolean) {
        webViewEventListener?.onLoadResource(url, hasVideo)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        var returnValue = false

        val event = MotionEvent.obtain(ev)
        val action = event.action

        if (action == MotionEvent.ACTION_DOWN) mNestedOffsetY = 0f

        val eventY = event.y
        event.offsetLocation(0f, mNestedOffsetY) // TODO

        when(action) {
            MotionEvent.ACTION_MOVE -> {
                var deltaY = (mLastY - eventY).toInt()

                if(dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1]
                    mLastY = eventY - mScrollOffset[1]
                    event.offsetLocation(0f, -mScrollOffset[1].toFloat())
                    mNestedOffsetY += mScrollOffset[1]
                }

                returnValue = super.onTouchEvent(event)

                if(dispatchNestedScroll(0, mScrollOffset[1], 0, deltaY, mScrollOffset)) {
                    event.offsetLocation(0f, mScrollOffset[1].toFloat())
                    mNestedOffsetY += mScrollOffset[1]
                    mLastY -= mScrollOffset[1]
                }
            }
            MotionEvent.ACTION_DOWN -> {
                returnValue = super.onTouchEvent(event)
                mLastY = eventY
                startNestedScroll(View.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                returnValue = super.onTouchEvent(event)
                stopNestedScroll()
            }
        }

        return returnValue
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) { helper.isNestedScrollingEnabled = enabled }

    override fun isNestedScrollingEnabled(): Boolean = helper.isNestedScrollingEnabled

    override fun startNestedScroll(axes: Int): Boolean = helper.startNestedScroll(axes)

    override fun stopNestedScroll() = helper.stopNestedScroll()

    override fun hasNestedScrollingParent(): Boolean = helper.hasNestedScrollingParent()

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean
        = helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean
        = helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean
        = helper.dispatchNestedFling(velocityX, velocityY, consumed)

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean
        = helper.dispatchNestedPreFling(velocityX, velocityY)
}