package com.abduhanifan.dicoding.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abduhanifan.dicoding.githubuserapp.alarm.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setTitle(R.string.setting)
        supportActionBar?.elevation = 0F

        btnSetting.setOnClickListener(this)
        btnOn.setOnClickListener(this)
        btnOff.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSetting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.btnOn -> {
                val message = "Periksa aplikasi Github User App sekarang!"
                alarmReceiver.setRepeatingAlarm(this, message)
            }
            R.id.btnOff -> alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
        }
    }
}