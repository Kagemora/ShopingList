package com.example.shopinglist.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R
import com.example.shopinglist.databinding.ActivityMainBinding
import com.example.shopinglist.presentation.viewmodel.MainViewModel
import com.example.shopinglist.presentation.adapter.ShopListAdapter

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)//новый метод submitList для ресайклервью
            //если нужно обновить список вызываем submitList
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                lauchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Succes", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun lauchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        with(binding.rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            setupLongClickListener()
            setupClickListener()
            setupSwipeLisnere(binding.rvShopList)
        }

    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                lauchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun setupSwipeLisnere(rvShopList: RecyclerView?) {
        val callbakc = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            //параметр 0 говорит, что мы не поддерживаем перетаскивание элементво списка
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {//игнорируем метод перетаскивания
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item =
                    shopListAdapter.currentList[viewHolder.adapterPosition] //используем новый метод для получения текущей позиции элемента
                //currentList если нужно получить текущий список с которым работает адаптер 
                viewModel.deleteShopItem(item)//удаляем из базы
            }

        }
        val itemTouchHelper =
            ItemTouchHelper(callbakc)//Создание экземпляра ItemTouchHelper, передавая в качестве параметра ранее созданный callback.
        itemTouchHelper.attachToRecyclerView(rvShopList)//Привязка ItemTouchHelper к RecyclerView, таким образом, активируя функциональность свайпа для элементов списка.
        //передали в attachToRecyclerView как должен работать спайп
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}
