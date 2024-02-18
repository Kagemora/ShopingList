package com.example.shopinglist.domain

class GetShopItemUseCase(private val shopListRepository:ShopListRepository) {
    fun getShopItem(shopItem: ShopItem):ShopItem{
        return shopListRepository.getShopItem(shopItem)
    }
}