package com.example.shopinglist.di

import android.app.Application
import com.example.shopinglist.data.bd.AppDatabase
import com.example.shopinglist.data.bd.ShopListDao
import com.example.shopinglist.data.repository.ShopListRepositoryImpl
import com.example.shopinglist.domain.repository.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindShopListRepository(shopListRepositoryImpl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun bindShopListDao(
            application: Application
        ): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }

}