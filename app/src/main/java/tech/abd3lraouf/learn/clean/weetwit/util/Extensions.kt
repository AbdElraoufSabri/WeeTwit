package tech.abd3lraouf.learn.clean.weetwit.util

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

fun String.userImageToSized(size: ImageSizes): String {
    return this.replace("normal", size.sizeString)
}

fun <T> Single<T>.defaultObserverAndSubscribe(): Single<T>? {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun String.toSimpleDateString(): String {
    val parseDf =
        SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.getDefault()) // Twitter Date Format
    val parsedDate = parseDf.parse(this) ?: return this

    val newDf = SimpleDateFormat("MM/dd/yy", Locale.getDefault()) // Simplere date format
    return newDf.format(parsedDate)
}

