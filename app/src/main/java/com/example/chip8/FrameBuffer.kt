package com.example.chip8

import android.util.Log

/**
 * date：2021/10/12
 * author：JerryChen
 */
class FrameBuffer {

    companion object {
        const val WIDTH = 64
        const val HEIGHT = 32
    }

    val frameBuffer = IntArray(WIDTH * HEIGHT)

    fun pixel(x: Int, y: Int): Int = frameBuffer[y * WIDTH + x]

    fun setPixel(x: Int, y: Int, v: Int) {
        frameBuffer[y * WIDTH + x] = v
    }

    fun clear() {
        frameBuffer.fill(0)
    }

    fun show(listener: OnDrawListener?) {
        var frame = ""
        repeat(HEIGHT) { y ->
            var line = ""
            repeat(WIDTH) { x ->
                val c = if (pixel(x, y) == 0) "□" else "■"
                line += c
            }
            Log.d(MainActivity.TAG, "$line")
            frame += "$line\n"
        }
//        Log.d(MainActivity.TAG, "\n\n")
        listener?.onDraw(frame)
    }
}