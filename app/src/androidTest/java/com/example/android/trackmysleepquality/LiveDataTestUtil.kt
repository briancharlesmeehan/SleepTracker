/*
 * Code copied from the Google Android Architecture Components "BasicSample"
 *
 * Function getValue() retrieves value from LiveData for testing purposes
 *
 */

package com.example.android.trackmysleepquality

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class LiveDataTestUtil {

    companion object {
        /**
         * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
         * Once we get a notification via onChanged(), we stop observing.
         */
        @Throws(InterruptedException::class)
        fun <T> getValue(liveData: LiveData<T>): T {
            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(@Nullable o: T) {
                    data[0] = o
                    latch.countDown()
                    liveData.removeObserver(this)
                }
            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            return data[0] as T
        }
    }
}