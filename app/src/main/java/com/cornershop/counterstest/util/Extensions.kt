package com.cornershop.counterstest.util

import android.util.Log

fun <T : Any> T.tag() = this::class.simpleName ?: ""
fun <T : Any> T.logD(message: String) = Log.d(this::class.simpleName, message)
fun <T : Any> T.logE(message: String) = Log.e(this::class.simpleName, message)