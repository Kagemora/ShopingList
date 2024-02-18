package com.example.shopinglist.domain

class DeleteItemUseCase(private val shopListRepository:ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem){
        shopListRepository.deleteShopItem(shopItem)
    }
}