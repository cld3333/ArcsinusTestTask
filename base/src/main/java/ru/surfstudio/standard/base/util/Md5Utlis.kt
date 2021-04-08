package ru.surfstudio.standard.base.util

import java.math.BigInteger
import java.security.MessageDigest

private const val ALGORITHM_NAME = "MD5"
private const val SIGNUM = 1
private const val RADIX = 16
private const val PAD_START = 32
private const val PAD_CHAR = '0'

fun getMd5Hash(text: String): String {
    val md = MessageDigest.getInstance(ALGORITHM_NAME)
    return BigInteger(SIGNUM, md.digest(text.toByteArray()))
            .toString(RADIX)
            .padStart(PAD_START, PAD_CHAR)
}