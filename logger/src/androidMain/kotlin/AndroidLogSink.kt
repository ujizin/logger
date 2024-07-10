package de.halfbit.logger.sink.android

import android.util.Log
import de.halfbit.logger.LogLevel
import de.halfbit.logger.LoggerBuilder
import de.halfbit.logger.sink.LogSink
import kotlinx.datetime.Instant

public fun LoggerBuilder.registerAndroidLogSink() {
    replaceSink(AndroidLogSink)
}

internal object AndroidLogSink : LogSink {
    override fun log(level: LogLevel, tag: String, timestamp: Instant, message: String?, err: Throwable?) {
        val msg = if (message == null) "***" else "*** $message"
        when (level) {
            LogLevel.Debug -> Log.d(tag, msg)
            LogLevel.Info -> Log.i(tag, msg)
            LogLevel.Warning -> Log.w(tag, msg, err)
            LogLevel.Error -> Log.e(tag, msg, err)
        }
    }
}