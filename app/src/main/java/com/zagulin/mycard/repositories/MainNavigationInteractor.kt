package com.zagulin.mycard.repositories

import android.util.Log
import com.zagulin.mycard.models.NavigationEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigationInteractor @Inject constructor() : IMainNavigationInteractor {

    private val subject = PublishSubject.create<NavigationEvents>()

    override var selectedNewsId: Int? = null


    override fun getNavigationEventObservable(): Observable<NavigationEvents> {
        return subject.observeOn(AndroidSchedulers.mainThread())
    }

    override fun callEvent(event: NavigationEvents) {
        subject.onNext(event)
    }
}