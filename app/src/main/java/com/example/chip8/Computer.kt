package com.example.chip8

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * date：2021/10/11
 * author：JerryChen
 */
class Computer {

    var drawListener: OnDrawListener? = null

    var cpu: CPU = CPU()
    var cpuClockHz: Long = 500

    var frameBuffer = FrameBuffer()

    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var cpuFuture: ScheduledFuture<*>? = null
    private var timerFuture: ScheduledFuture<*>? = null

    // TODO 将byte转换成int，代表无符号的数
    // 但是转换关系还没看懂，稍后再看
    private fun unsigned(b: Byte): Int = if (b < 0) b + 0x10 else b.toInt()

    fun loadRom(romBytes: ByteArray) {
        cpu.loadRom(romBytes)
        launchTimers()
    }

    private fun nextInstruction(pc: Int = cpu.PC): Instruction {
        fun extract(pc: Int): Pair<Int, Int> {
            val b = cpu.memory[pc]
            val b0 = unsigned(b.toInt().shr(4).toByte())
            val b1 = unsigned(b.toInt().and(0xf).toByte())
            return Pair(b0, b1)
        }
        val (b0, b1) = extract(pc)
        val (b2, b3) = extract(pc + 1)

        return Instruction(this@Computer, b0, b1, b2, b3)
    }

    private fun launchTimers() {
        val cpuTick = Runnable {
            nextInstruction().run()
        }

        val timerTick = Runnable {
            if (cpu.DT > 0) {
                cpu.DT--
            }
            if (cpu.ST > 0) {
                // TODO 实现声音播放
                cpu.ST--
            }
        }

        cpuFuture =
            executor.scheduleAtFixedRate(cpuTick, 0, 1_000_000L / cpuClockHz, TimeUnit.MICROSECONDS)
        timerFuture = executor.scheduleAtFixedRate(timerTick, 0, 16L, TimeUnit.MILLISECONDS)
    }

}