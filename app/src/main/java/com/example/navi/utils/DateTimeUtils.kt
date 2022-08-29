package com.example.navi.utils

import java.text.SimpleDateFormat

object DateTimeUtils {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private val outputFormat = SimpleDateFormat("dd-MM-yyyy")

    fun getData(dateNTime: String): String {
        val input = inputFormat.parse(dateNTime)
        return outputFormat.format(input)
    }
}