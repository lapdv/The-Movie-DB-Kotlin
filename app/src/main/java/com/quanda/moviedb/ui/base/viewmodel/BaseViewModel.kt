package com.quanda.moviedb.ui.base.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    // rx
    val compoDisposable = CompositeDisposable()

    // coroutines
    val parentJob = Job()
    val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun addDisposable(disposable: Disposable) {
        compoDisposable.add(disposable)
    }

    fun onActivityDestroyed() {
        compoDisposable.clear()
        parentJob.cancel()
    }
}