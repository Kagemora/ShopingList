package com.example.shopinglist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)//новый метод submitList для ресайклервью
            //если нужно обновить список вызываем submitList
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener{
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLE,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        setupLongClickListener()
        setupClickListener()
        setupSwipeLisnere(rvShopList)

    }

    private fun setupClickListener() {
        adapter.onShopItemClickListener = {
            Log.d("MainActivity", it.toString())
            val intent = ShopItemActivity.newIntentEditItem(this,it.id)
            startActivity(intent)
        }
    }

    private fun setupSwipeLisnere(rvShopList: RecyclerView?) {
        val callbakc = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {//параметр 0 говорит, что мы не поддерживаем перетаскивание элементво списка
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {//игнорируем метод перетаскивания
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item =
                    adapter.currentList[viewHolder.adapterPosition] //используем новый метод для получения текущей позиции элемента
                //currentList если нужно получить текущий список с которым работает адаптер 
                viewModel.deleteShopItem(item)//удаляем из базы
            }

        }
        val itemTouchHelper = ItemTouchHelper(callbakc)//Создание экземпляра ItemTouchHelper, передавая в качестве параметра ранее созданный callback.
        itemTouchHelper.attachToRecyclerView(rvShopList)//Привязка ItemTouchHelper к RecyclerView, таким образом, активируя функциональность свайпа для элементов списка.
        //передали в attachToRecyclerView как должен работать спайп
    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}
