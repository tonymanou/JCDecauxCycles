package com.tonymanou.jcdecauxcycles.utils

import com.tonymanou.jcdecauxcycles.exception.RestResponseException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * Execute the observable on a thread pool, post the result on the main thread to the given
 * observer.
 *
 * @param observer The observer that will receive updates.
 */
fun <T> Observable<T>.executeAsync(observer: Observer<T>) {
    subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
}

/**
 * Wraps the HTTP query into an observable.
 *
 * @return An observable wrapping the query.
 */
fun <T> Call<T>.asObservable(): Observable<T> {
    return Observable.create { emitter ->
        val response = execute()
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                emitter.onNext(data)
            }
            emitter.onComplete()
        } else {
            val url = request().url()
            emitter.onError(RestResponseException(url!!, response.code()))
        }
    }
}
