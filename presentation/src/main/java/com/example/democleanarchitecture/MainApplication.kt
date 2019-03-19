package com.example.democleanarchitecture

import com.example.democleanarchitecture.util.di.component.DaggerAppComponent
import dagger.android.DaggerApplication

class MainApplication : DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()
}
