package tech.abd3lraouf.learn.clean.weetwit.util

import android.text.Editable
import android.text.TextWatcher
import java.util.*
import kotlin.concurrent.schedule

class DelayedTextWatcher(val onDelayedTextChanged: (text: String) -> Unit, private val timeDelay: Long = 500) :
    TextWatcher {

    companion object {
        const val TAG = "TextWatcherDelay"
    }

    private var timer = Timer(TAG)

    override fun onTextChanged(newString: CharSequence?, p1: Int, p2: Int, p3: Int) {
        resetTimer()
        scheduleTimer(newString.toString())
    }

    private fun resetTimer() {
        timer.cancel()
        timer = Timer(TAG)
    }

    private fun scheduleTimer(newString: String) {
        timer.schedule(timeDelay) {
            onDelayedTextChanged(newString)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}