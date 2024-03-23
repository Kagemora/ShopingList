package com.example.shopinglist.domain.usecase

import com.example.shopinglist.domain.entities.ShopItem
import com.example.shopinglist.domain.repository.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
   suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}