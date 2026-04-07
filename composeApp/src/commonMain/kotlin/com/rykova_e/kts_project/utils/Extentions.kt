package com.rykova_e.kts_project.utils

import kotlin.math.roundToInt

fun String.roundToDecimal(): String {
    return ((this.toDouble() * 100).roundToInt() / 100.0).toString()
}