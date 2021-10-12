package com.example.chip8

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CHIP-8"
    }

    private lateinit var screen: TextView

    private val computer = Computer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screen = findViewById(R.id.screen)

        // 加载ROM
        loadROM()

        computer.drawListener = object : OnDrawListener {
            override fun onDraw(frame: String) {
                screen.text = frame
            }
        }
    }

    private fun loadROM() {
        val romBytes = assets.open("PONG").readBytes()
        computer.loadRom(romBytes)
    }
}

interface OnDrawListener {
    fun onDraw(frame: String)
}