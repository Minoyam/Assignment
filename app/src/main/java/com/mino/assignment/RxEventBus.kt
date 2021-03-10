package com.mino.assignment

import io.reactivex.subjects.PublishSubject

object RxEventBus {
    val subject = PublishSubject.create<Int>()

    fun sendEvent(code: Int) {
        subject.onNext(code)
    }
    const val NO_DATA = 100
    const val HAVE_DATA = 101

}