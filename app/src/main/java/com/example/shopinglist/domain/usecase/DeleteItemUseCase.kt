package com.example.shopinglist.domain.usecase

import com.example.shopinglist.domain.entities.ShopItem
import com.example.shopinglist.domain.repository.ShopListRepository

class DeleteItemUseCase(private val shopListRepository: ShopListRepository) {
   suspend fun deleteShopItem(shopItem: ShopItem){
        shopListRepository.deleteShopItem(shopItem)
    }
}