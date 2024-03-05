package com.example.shopinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopinglist.domain.ShopItem
@Entity(tableName = "shop_items")
class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,

)