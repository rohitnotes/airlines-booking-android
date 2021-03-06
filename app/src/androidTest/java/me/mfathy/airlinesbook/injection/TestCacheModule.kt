package me.mfathy.airlinesbook.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import org.mockito.Mockito.mock

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun provideDatabase(application: Application): AirportsDatabase {
        return AirportsDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun provideCacheStore(): AirportsDataStore {
        return mock(AirportsDataStore::class.java)
    }

}