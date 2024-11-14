package edu.uw.ischool.hyoon719.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        val phone = extras?.getString("PHONE")
        val message = extras?.getString("MESSAGE")

        if (!message.isNullOrEmpty() && !phone.isNullOrEmpty()) {
            try {
                SmsManager.getDefault().sendTextMessage(phone, null, message, null, null)
                Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show() // In order to test in the emulator.
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to send SMS: ${e.message}", Toast.LENGTH_SHORT).show() // Catch the exception thrown
            }
        }
    }
}