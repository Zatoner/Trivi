package com.aboe.trivilauncher.domain.use_case.get_formatted_date

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetFormattedDateUseCase @Inject constructor() {

    operator fun invoke(): String {
        val calendar = Calendar.getInstance()

        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfMonthSuffix = getDayOfMonthSuffix(dayOfMonth)
        val month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)

        return "$dayOfWeek ${dayOfMonth}$dayOfMonthSuffix, $month"
    }

    private fun getDayOfMonthSuffix(day: Int): String {
        return when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
    }
}