package edu.uw.ischool.hyoon719.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

    lateinit var pendingIntent: PendingIntent
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewRoot = layoutInflater.inflate(R.layout.activity_main, null)
        setContentView(viewRoot)

        val messageInput: EditText = findViewById(R.id.messageInput)
        val phoneInput: EditText = findViewById(R.id.phoneInput)
        val minuteInput: EditText = findViewById(R.id.minuteInput)
        val button: Button = findViewById(R.id.button)
        button.setEnabled(false)

        var isMessageFilled = false
        var isPhoneFilled = false
        var isTimeFilled = false
        var messageOutput = ""
        var phoneOutput = ""
        var minuteOutput = 0

        val alarmManager = getSystemService(ALARM_SERVICE) as? AlarmManager

        messageInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!messageInput.getText().isEmpty()) {
                    messageOutput = s.toString()
                } else {
                    isMessageFilled = false
                    button.setEnabled(false)
                }
            }
            override fun afterTextChanged(s: Editable?) {
                isMessageFilled = !s.isNullOrEmpty()
                if (isMessageFilled && isPhoneFilled && isTimeFilled) {
                    button.setEnabled(true)
                }
            }
        })

        phoneInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!phoneInput.getText().isEmpty()) {
                    phoneOutput = s.toString()
                } else {
                    isPhoneFilled = false
                    button.setEnabled(false)
                }
            }
            override fun afterTextChanged(s: Editable?) {
                isPhoneFilled = !s.isNullOrEmpty()
                if (isMessageFilled && isPhoneFilled && isTimeFilled) {
                    button.setEnabled(true)
                }
            }
        })

        minuteInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!minuteInput.getText().isEmpty()) {
                    minuteOutput = s.toString().toInt()
                } else {
                    isTimeFilled = false
                    button.setEnabled(false)
                }
            }
            override fun afterTextChanged(s: Editable?) {
                isTimeFilled = !s.isNullOrEmpty()
                if (isMessageFilled && isPhoneFilled && isTimeFilled) {
                    button.setEnabled(true)
                }
            }
        })

        var isClicked = false
        button.setOnClickListener{
            if(isClicked) {
                button.text = "Start"
                alarmManager!!.cancel(pendingIntent)
                messageInput.setEnabled(true)
                phoneInput.setEnabled(true)
                minuteInput.setEnabled(true)
            } else {
                button.text = "Stop"
                messageInput.setEnabled(false)
                phoneInput.setEnabled(false)
                minuteInput.setEnabled(false)
                val intent = Intent(this, AlarmReceiver::class.java)
                val message = "${phoneOutput}:${messageOutput}"
                intent.putExtra("MESSAGE", message)
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager!!.setRepeating(
                    AlarmManager.RTC,
                    System.currentTimeMillis() + minuteOutput * 60000L,
                    minuteOutput * 60000L,
                    pendingIntent
                )
            }
            isClicked = !isClicked
        }
    }
}
