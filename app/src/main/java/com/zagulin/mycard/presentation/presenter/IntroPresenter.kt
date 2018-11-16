package com.zagulin.mycard.presentation.presenter

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zagulin.mycard.App
import com.zagulin.mycard.presentation.view.IntroView
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@InjectViewState
class IntroPresenter : MvpPresenter<IntroView>() {

    companion object {
        private const val TIME_TO_SHOW_INTRO: Long = 5
        private const val SHOULD_SHOW_INTRO_PREF = "should_show_intro"
    }


    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScope(App.Companion.Scopes.APP_SCOPE.name))
        if (needToSHowIntro()) {
            viewState.showIntroActivity()
            val disposable = Completable.complete()
                    .delay(TIME_TO_SHOW_INTRO, TimeUnit.SECONDS)
                    .subscribeBy {
                        viewState.moveToFeedActivity()
                    }
            compositeDisposable.add(disposable)

        } else {
            viewState.moveToFeedActivity()
        }
    }


    private fun needToSHowIntro(): Boolean {
        val result = sharedPreferences.getBoolean(SHOULD_SHOW_INTRO_PREF, true)
        sharedPreferences.edit().putBoolean(SHOULD_SHOW_INTRO_PREF, !result).apply()
        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}