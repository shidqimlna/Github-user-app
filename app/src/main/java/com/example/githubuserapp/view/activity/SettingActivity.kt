package com.example.githubuserapp.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.broadcast.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    var alarmReceiver = AlarmReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        activity_setting_button_on.setOnClickListener {
            alarmReceiver.setRepeatingAlarm(this)
        }
        activity_setting_button_off.setOnClickListener {
            alarmReceiver.cancelAlarm(this)
        }

    }
}