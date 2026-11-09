package com.example.attendance

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONArray

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
        val web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.addJavascriptInterface(Bridge(this), "Android")
        web.webViewClient = WebViewClient()
        web.loadUrl("file:///android_asset/index.html")
        setContentView(web)
    }

    class Bridge(private val ctx: Context) {
        private fun pi(id: Int, t: String, x: String): PendingIntent {
            val i = Intent(ctx, AlarmReceiver::class.java)
                .putExtra("t", t).putExtra("x", x).putExtra("id", id)
            return PendingIntent.getBroadcast(
                ctx, id, i, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        @JavascriptInterface
        fun setAlarms(json: String) {
            val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val sp = ctx.getSharedPreferences("alarms", Context.MODE_PRIVATE)
            sp.getStringSet("ids", emptySet())!!.forEach { am.cancel(pi(it.toInt(), "", "")) }
            val show = PendingIntent.getActivity(
                ctx, 0, Intent(ctx, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
            )
            val arr = JSONArray(json)
            val ids = HashSet<String>()
            for (k in 0 until arr.length()) {
                val o = arr.getJSONObject(k)
                val id = o.getInt("id")
                val at = o.getLong("at")
                if (at > System.currentTimeMillis()) {
                    am.setAlarmClock(
                        AlarmManager.AlarmClockInfo(at, show),
                        pi(id, o.getString("t"), o.getString("x"))
                    )
                    ids.add(id.toString())
                }
            }
            sp.edit().putStringSet("ids", ids).apply()
        }
    }
}
