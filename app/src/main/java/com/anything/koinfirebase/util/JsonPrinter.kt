package com.anything.koinfirebase.util

import android.util.Log
import com.google.gson.GsonBuilder

class JsonPrinter(clazz: Class<*>) {

    private val gson = GsonBuilder()
        .serializeSpecialFloatingPointValues()
        .serializeNulls()
        .setPrettyPrinting().create()

    private val className: String = clazz.simpleName

    private val level: String
        get() = ALL

    fun info(o: Any): String? {
        if (level == ALL || level == INFO) {
            val msg = gson.toJson(o)
            Log.i(className, msg)
            return msg
        }
        return null
    }

    fun warn(o: Any): String? {
        if (level == ALL || level == WARN) {
            val msg = gson.toJson(o)
            Log.i(className, msg)
            return msg
        }
        return null
    }

    fun error(o: Any): String? {
        if (level == ALL || level == ERROR) {
            val msg = gson.toJson(o)
            Log.i(className, msg)
            return msg
        }
        return null
    }

    fun debug(o: Any): String? {
        if (level == ALL || level == DEBUG) {
            val msg = gson.toJson(o)
            Log.i(className, msg)
            return msg
        }
        return null
    }

    fun verbose(o: Any): String? {
        if (level == ALL || level == VERBOSE) {
            val msg = gson.toJson(o)
            Log.i(className, msg)
            return msg
        }
        return null
    }


    companion object {
        const val ALL = "all"
        const val INFO = "info"
        const val DEBUG = "debug"
        const val ERROR = "error"
        const val WARN = "warn"
        const val VERBOSE = "verbose"
    }

}