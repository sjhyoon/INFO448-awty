package edu.uw.ischool.hyoon719.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("MESSAGE")
        if (!message.isNullOrEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}