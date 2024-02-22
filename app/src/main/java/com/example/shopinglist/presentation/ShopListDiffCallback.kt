package com.example.shopinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shopinglist.domain.ShopItem

class ShopListDiffCallback(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemOld = oldList[oldItemPosition]
        val itemNew = newList[newItemPosition]
        return itemOld.id == itemNew.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemOld = oldList[oldItemPosition]
        val itemNew = newList[newItemPosition]
        return itemOld == itemNew
    }

}