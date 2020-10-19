package com.example.moviesapp.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object DateUtil {

    fun getReleaseDate(dateString: String): String {
        val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru")))
    }

    fun getRunningTime(timeInMinutes: Int): String {
        val hour = timeInMinutes / 60
        val min = timeInMinutes % 60
        return "${hour}h ${if (min > 9) min else "0$min"}min"
    }
}