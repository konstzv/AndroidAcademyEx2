package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.NavigationEvents
import io.reactivex.Observable

interface IMainNavigationInteractor {

    fun getNavigationEventObservable(): Observable<NavigationEvents>
    fun callEvent(event: NavigationEvents)
    var selectedNewsId: Int?
}
