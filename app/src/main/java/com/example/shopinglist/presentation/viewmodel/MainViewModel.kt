package com.example.shopinglist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopinglist.data.repository.ShopListRepositoryImpl
import com.example.shopinglist.domain.usecase.DeleteItemUseCase
import com.example.shopinglist.domain.usecase.EditShopItemUseCase
import com.example.shopinglist.domain.usecase.GetShopListUseCase
import com.example.shopinglist.domain.entities.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) :AndroidViewModel(application){
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem){
        viewModelScope.launch{
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    }
    fun changeEnableState(shopItem: ShopItem){
        viewModelScope.launch{
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }

    }

}