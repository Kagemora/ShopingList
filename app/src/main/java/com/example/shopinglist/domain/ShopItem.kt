package com.example.shopinglist.domain

data class ShopItem(
    val name:String,
    val count:Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID //magic number
){
    companion object{
        const val UNDEFINED_ID = -1
    }

}
