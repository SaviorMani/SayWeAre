package com.manideep.sayweare.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.manideep.sayweare.AlarmActivity
import com.manideep.sayweare.R

object AlarmNotifier {
	private const val CHANNEL_ID = "emergency_alarm_channel"
	private const val NOTIFICATION_ID = 1001
	const val ACTION_SAVIOR = "com.manideep.sayweare.ACTION_SAVIOR"
	const val ACTION_SORRY = "com.manideep.sayweare.ACTION_SORRY"

	fun ensureChannel(context: Context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name = "Emergency Alarm"
			val descriptionText = "High-importance emergency alerts"
			val importance = NotificationManager.IMPORTANCE_HIGH
			val channel = NotificationChannel(CHANNEL_ID, name, importance)
			channel.description = descriptionText
			channel.enableVibration(true)
			val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
			val audioAttributes = AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_ALARM)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
				.build()
			channel.setSound(alarmUri, audioAttributes)
			val notificationManager: NotificationManager =
				context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(channel)
		}
	}

	fun showAlarm(context: Context) {
		ensureChannel(context)

		val fullScreenIntent = Intent(context, AlarmActivity::class.java)
		val fullScreenPendingIntent = PendingIntent.getActivity(
			context,
			0,
			fullScreenIntent,
			PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
		)

		val saviorIntent = Intent(context, AlarmActionReceiver::class.java).apply {
			action = ACTION_SAVIOR
		}
		val saviorPending = PendingIntent.getBroadcast(
			context, 1, saviorIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
		)

		val sorryIntent = Intent(context, AlarmActionReceiver::class.java).apply {
			action = ACTION_SORRY
		}
		val sorryPending = PendingIntent.getBroadcast(
			context, 2, sorryIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
		)

		val title = context.getString(R.string.app_name)
		val content = "It's an Emergency"

		val builder = NotificationCompat.Builder(context, CHANNEL_ID)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setContentTitle(title)
			.setContentText(content)
			.setPriority(NotificationCompat.PRIORITY_MAX)
			.setCategory(NotificationCompat.CATEGORY_CALL)
			.setOngoing(true)
			.setAutoCancel(false)
			.setFullScreenIntent(fullScreenPendingIntent, true)
			.addAction(0, "I'm the Savior", saviorPending)
			.addAction(0, "I'm Sorry", sorryPending)
			.setStyle(NotificationCompat.BigTextStyle().bigText(content))
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))

		NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
	}

	fun cancel(context: Context) {
		NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
	}
}