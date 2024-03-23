package com.example.shopinglist.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shopinglist.domain.entities.ShopItem
import com.example.shopinglist.domain.repository.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

   fun getShopList():LiveData<List<ShopItem>>{
        return shopListRepository.getShopList()
    }
}