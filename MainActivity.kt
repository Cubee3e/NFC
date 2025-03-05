package com.example.nfcapp

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        // Получаем NFC адаптер
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // Проверяем, поддерживает ли устройство NFC
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC не поддерживается этим устройством", Toast.LENGTH_SHORT).show()
            return
        }

        // Проверяем, активирован ли NFC
        if (!nfcAdapter.isEnabled) {
            Toast.makeText(this, "NFC выключен. Пожалуйста, включите NFC в настройках", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, null, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        // Получаем действие (например, при нахождении метки NFC)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val nfcTag = intent.getParcelableExtra<android.nfc.Tag>(NfcAdapter.EXTRA_TAG)
            val tagInfo = nfcTag?.id?.joinToString("-")
            textView.text = "Mетка: $tagInfo"
        }
    }
}
