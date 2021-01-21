package com.task.di

import com.anand.demo.application.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(app: App)
}
