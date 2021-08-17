package blog.cmcmcmcm.webvideoarchiving.util.state

/**
 * State
 */
sealed class DatabaseResult<out T> {
    class Init : DatabaseResult<Nothing>()
    class Loading : DatabaseResult<Nothing>()
    class Success<out T>(val message : T) : DatabaseResult<T>()
    class Error(val throwable: Throwable) : DatabaseResult<Nothing>()
}