package com.noxinfinity.pdate.utils.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
    companion object {
        fun formatToDDMMYYYY(date: String?): String {
            if(date.isNullOrBlank()) {
                return ""
            }

            if(date[4] != '-') {
                return date
            }

            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")

            val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val dateTime = LocalDateTime.parse(date, inputFormat)

            return dateTime.format(outputFormat)
        }
    }
}