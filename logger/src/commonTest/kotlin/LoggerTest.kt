package de.halfbit.logger.sink

import de.halfbit.logger.LogLevel
import de.halfbit.logger.d
import de.halfbit.logger.initializeLogger
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertTrue

class LoggerTest {

    @Test
    fun sinksOrDifferentTypesDoNotReplaceEachOther() {
        // given
        val sink1 = Sink1()
        val sink2 = Sink2()
        val sink3 = Sink3()

        initializeLogger {
            replaceSink(sink1)
            replaceSink(sink2)
            replaceSink(sink3)
        }

        // when
        d("test") { "message" }

        // then
        assertTrue(sink1.received)
        assertTrue(sink2.received)
        assertTrue(sink3.received)
    }
}

class Sink1 : LogSink {
    var received: Boolean = false
    override fun log(level: LogLevel, tag: String, timestamp: Instant, message: String?, err: Throwable?) {
        received = true
    }
}

class Sink2 : LogSink {
    var received: Boolean = false
    override fun log(level: LogLevel, tag: String, timestamp: Instant, message: String?, err: Throwable?) {
        received = true
    }
}

class Sink3 : LogSink {
    var received: Boolean = false
    override fun log(level: LogLevel, tag: String, timestamp: Instant, message: String?, err: Throwable?) {
        received = true
    }
}