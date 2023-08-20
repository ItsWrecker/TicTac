package com.wrecker.tictac

import android.graphics.drawable.GradientDrawable.Orientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var grid: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        grid = findViewById(R.id.mainGrid)

        adapter = RecyclerViewAdapter()


        lifecycleScope.launch {
            viewModel._state.collectLatest {
                Log.i("ONCLICK", it.cells.toString())
                grid.layoutManager = GridLayoutManager(this@MainActivity,3)
                grid.adapter = adapter
                adapter.differ.submitList(it.cells)
                adapter.onItemClick { id ->
                    Log.i("OK", id.toString())
                    if (it.cells[id].third){
                        viewModel.selectCell(id)
                    }
                }
            }
        }

    }
}