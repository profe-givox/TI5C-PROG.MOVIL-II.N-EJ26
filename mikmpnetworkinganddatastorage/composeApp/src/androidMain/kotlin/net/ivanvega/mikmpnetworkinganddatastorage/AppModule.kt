package net.ivanvega.mikmpnetworkinganddatastorage

import net.ivanvega.mikmpnetworkinganddatastorage.cache.AndroidDatabaseDriverFactory
import net.ivanvega.mikmpnetworkinganddatastorage.network.SpaceXApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<SpaceXApi> { SpaceXApi() }
    single<SpaceXSDK> {
        SpaceXSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ), api = get()
        )
    }
}