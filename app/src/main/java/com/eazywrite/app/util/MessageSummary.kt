package com.eazywrite.app.util

import java.math.BigInteger
import java.security.MessageDigest


fun messageSummary(input: String, algorithm: String): String {
    val md = MessageDigest.getInstance(algorithm)
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}
