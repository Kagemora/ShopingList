package com.example.shopinglist.data

import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)//старый элемент находим по id, удаляем
        shopList.remove(oldElement)
        addShopItem(shopItem)//такой же, но измененный добавляем
    }

    override fun getShopItem(shopItem: Int): ShopItem {
        return shopList.find { it.id == shopItem }
            ?: throw RuntimeException("Element with id $shopItem not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}