package blog.cmcmcmcm.webvideoarchiving.view.exoplayer

import android.support.annotation.IntDef

interface OnOrientationChangeListener {

    companion object {
        const val PORTRAIT = 0
        const val LANDSCAPE = 1
    }

    @IntDef(PORTRAIT, LANDSCAPE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class OrientationType

    /**
     * 디바이스 방향 변경 함수
     * @param orientation 디바이스 방향
     */
    fun onOrientationChanged(@OrientationType orientation : Int)
}