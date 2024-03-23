package com.example.shopinglist.domain.repository

import androidx.lifecycle.LiveData
import com.example.shopinglist.domain.entities.ShopItem

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(shopItem: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
}