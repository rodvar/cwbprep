package com.rodvar.cwbprep.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rodvar on 22/1/18.
 */
class DateUtil {
    companion object {
        val TZ = TimeZone.getTimeZone("Australia/Sydney")
        private val DEFAULT_DATE_FORMAT = "dd MMMM yyyy"
        val SYDNEY_DAY_FORMATTER = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US)
        var df: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        /**
         * @return Formatted date always in the Australia/Melbourne TZ, regardless of TZ of device.
         */
        fun formatDate(dateString: String): String {
            val date = this.parseDateString(dateString)
            val calendar = Calendar.getInstance(TZ)
            calendar.time = date
            return SYDNEY_DAY_FORMATTER.format(date)
        }

        fun parseDateString(dateString: String): Date = df.parse(dateString)
    }
}