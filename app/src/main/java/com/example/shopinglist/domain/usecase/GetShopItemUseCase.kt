package com.example.shopinglist.domain.usecase

import com.example.shopinglist.domain.entities.ShopItem
import com.example.shopinglist.domain.repository.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
   suspend fun getShopItem(shopItem: Int): ShopItem {
        return shopListRepository.getShopItem(shopItem)
    }
}