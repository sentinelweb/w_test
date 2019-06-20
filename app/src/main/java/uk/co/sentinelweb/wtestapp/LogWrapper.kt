package uk.co.sentinelweb.wtestapp

import android.util.Log

class LogWrapper {

    fun d(tag:String, msg:String, e:Throwable?) {
        Log.d(tag, msg, e)
    }
}