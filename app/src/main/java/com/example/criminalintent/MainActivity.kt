package com.example.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.criminalintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = IntentListAdapter()
    private val imgIdList = listOf(R.drawable.black_box, R.drawable.black_box)
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            rcView.adapter = adapter
            buttonAdd.setOnClickListener {
                if (index < imgIdList.size - 1) {
                    var intent = IntentModel("Kill human", "10 Jan 2022", imgIdList[index])
                    adapter.addIntent(intent)
                    index++
                } else {
                    index = 0
                }
            }
        }
    }
}