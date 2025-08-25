package com.manideep.sayweare.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmActionReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		when (intent.action) {
			AlarmNotifier.ACTION_SAVIOR -> {
				Toast.makeText(context, "You claimed: I'm the Savior", Toast.LENGTH_LONG).show()
				AlarmNotifier.cancel(context)
			}
			AlarmNotifier.ACTION_SORRY -> {
				Toast.makeText(context, "You responded: I'm Sorry", Toast.LENGTH_LONG).show()
				AlarmNotifier.cancel(context)
			}
		}
	}
}