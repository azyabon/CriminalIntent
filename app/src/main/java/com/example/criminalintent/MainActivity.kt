package com.example.criminalintent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.criminalintent.databinding.ActivityMainBinding
import com.example.criminalintent.db.IntentDataBase
import com.example.criminalintent.db.repository.IntentRealization

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = IntentListAdapter()

    private val imgIdList = listOf(R.drawable.black_box, R.drawable.black_box)
    private var index = 0

    private var fragment =  IntentFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP = this
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        } else if (item.itemId == R.id.add) {
            supportFragmentManager.beginTransaction().replace(R.id.intentForm, fragment).commit()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        return true
    }

    private fun initDataBase() {
        val daoIntent = IntentDataBase.getInstance(this).getIntentDao()
        REPOSITORY = IntentRealization(daoIntent)
    }

    fun getAllIntents(): LiveData<List<IntentModel>> {
        return REPOSITORY.allIntents
    }

    private fun init() {
        initDataBase()
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            rcView.adapter = adapter
            getAllIntents().observe(this@MainActivity) { listIntents ->
                listIntents.asReversed()
                adapter.setList(listIntents)
            }
        }
    }
}
