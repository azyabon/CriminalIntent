package com.example.criminalintent

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.criminalintent.data.Model.Photos
import com.example.criminalintent.data.repository.Repository
import com.example.criminalintent.databinding.ActivityMainBinding
import com.example.criminalintent.db.IntentDataBase
import com.example.criminalintent.db.repository.IntentRealization
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.list_item_model.*
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalTime
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var repo = Repository()
    var photoList: MutableLiveData<Response<Photos>> = MutableLiveData()
    private val adapter = IntentListAdapter()

    private var fragment =  IntentFragment.newInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP = this
        init()
        initDataBase()

        createNotificationChannel();

        val search = findViewById<TextInputEditText>(R.id.search)

        search.doOnTextChanged { text, start, count, after ->
                if (text!!.isEmpty()) {
                    init()
                } else {
                    outputPhotos(text.toString())
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel";
        val desc = "A Description of the channel";
        val importance = NotificationManager.IMPORTANCE_DEFAULT;
        val channel = NotificationChannel(channelID, name, importance);
        channel.description = desc;
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(applicationContext, Notification::class.java)
        val calendar = Calendar.getInstance();
        intent.putExtra(titleExtra, "You are logged into the photo gallery app")
        intent.putExtra(messageExtra, "Enjoy watching!")

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = calendar.timeInMillis
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
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

    fun getPhotos() {
        lifecycleScope.launch {
            photoList.value = repo.getPhotos()
        }
    }

    fun searchPhotos(tag: String) {
        lifecycleScope.launch {
            photoList.value = repo.getTagsPhotos(tag)
        }
    }

    fun outputPhotos(tag: String) {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 3)
            rcView.adapter = adapter
            searchPhotos(tag)
            photoList.observe(this@MainActivity) {  list ->
                list.body()?.photos?.let { adapter.setList(it.photo) }
            }
        }
    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 3)
            rcView.adapter = adapter
            getPhotos()
            photoList.observe(this@MainActivity) {  list ->
                list.body()?.photos?.let { adapter.setList(it.photo) }
            }
        }
    }
}
