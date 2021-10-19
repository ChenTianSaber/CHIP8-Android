package com.example.chip8

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CHIP-8"
    }

    private lateinit var screen: TextView
    private lateinit var leftUp: Button
    private lateinit var leftDown: Button

    private val computer = Computer(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screen = findViewById(R.id.screen)
        leftUp = findViewById(R.id.left_up)
        leftDown = findViewById(R.id.left_down)

        // 1 是左边向上，4 是左边向下
        // c 是右边向上，d 是右边向下
        // 1 2 3 C
        // 4 5 6 D
        // 7 8 9 E
        // A 0 B F
        leftUp.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> computer.keyboard.key = null
                else -> computer.keyboard.key = Integer.parseInt("1", 16)
            }
            true
        }

        leftDown.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> computer.keyboard.key = null
                else -> computer.keyboard.key = Integer.parseInt("4", 16)
            }
            true
        }

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