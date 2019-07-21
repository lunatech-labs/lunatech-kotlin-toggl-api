package utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.security.MessageDigest
import java.util.*

object TimeUtil {
    /**
     * Converts seconds to hours and round hours to a quarter. This function rounds the hours the same Toggl does by
     * rounding minutes always up to a quarter. Eg. 1h03 and 1h14 will be both rounded to 1h15.
     *
     * @param seconds the seconds to convert
     * @return a float rounded to a quarter, representing the number of hours
     */
    fun toHours(seconds: Long) = if (seconds > 0) Math.ceil(((seconds / 3600) * 4).toDouble()).toFloat() / 4f else 0f


//    fun dateTimeOrdering: Ordering[DateTime] = Ordering.fromLessThan(_ isBefore _)

}

object Time {

    private val zone = DateTimeZone.forID("Europe/Amsterdam")
    private val tz = TimeZone.getTimeZone("Europe/Amsterdam")

    fun ordinal(date: DateTime) = run {
        val cal: Calendar = Calendar.getInstance()
        cal.time = date.toDateTime(zone).toDate()
        cal.timeZone = tz
        val num = cal.get(Calendar.DAY_OF_MONTH)
        val suffix = arrayListOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        val m = num % 100
        val index = if (m in 11..19) 0 else m % 10
        num.toString() + suffix[index]
    }

    fun md5(s: String) =
        MessageDigest.getInstance("MD5").digest(s.toByteArray()).map { "%02X".format() }.joinToString().toLowerCase()
}
