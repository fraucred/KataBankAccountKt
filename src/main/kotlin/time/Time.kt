package time

import java.time.LocalDateTime

class Time {
    companion object {
        fun getCurrentTime(): LocalDateTime {
            return LocalDateTime.now()
        }
    }
}