package com.example.chip8

import java.util.*

/**
 * date：2021/10/11
 * author：JerryChen
 */
class CPU {

    /** 程序计数器 */
    var PC: Int = 0x200

    /** V 寄存器 */
    val V: IntArray = IntArray(16)

    /** I 寄存器 */
    var I: Int = 0

    /** 延迟定时器 */
    var DT: Int = 0

    /** 声音定时器 */
    var ST: Int = 0

    /** 栈 */
    val SP = Stack<Int>()

    val memory = ByteArray(4096)

    /** 字体数据 */
    private val FONT_SPRITES = arrayOf(
        0xF0, 0x90, 0x90, 0x90, 0xF0, // '0' at 0x00
        0x20, 0x60, 0x20, 0x20, 0x70, // '1' at 0x05
        0xF0, 0x10, 0xF0, 0x80, 0xF0, // '2' at 0x0A
        0xF0, 0x10, 0xF0, 0x10, 0xF0, // '3' at 0x0F
        0x90, 0x90, 0xF0, 0x10, 0x10, // '4' at 0x14
        0xF0, 0x80, 0xF0, 0x10, 0xF0, // '5' at 0x19
        0xF0, 0x80, 0xF0, 0x90, 0xF0, // '6' at 0x1E
        0xF0, 0x10, 0x20, 0x40, 0x40, // '7' at 0x23
        0xF0, 0x90, 0xF0, 0x90, 0xF0, // '8' at 0x28
        0xF0, 0x90, 0xF0, 0x10, 0xF0, // '9' at 0x2D
        0xF0, 0x90, 0xF0, 0x90, 0x90, // 'A' at 0x32
        0xE0, 0x90, 0xE0, 0x90, 0xE0, // 'B' at 0x37
        0xF0, 0x80, 0x80, 0x80, 0xF0, // 'C' at 0x3C
        0xE0, 0x90, 0x90, 0x90, 0xE0, // 'D' at 0x41
        0xF0, 0x80, 0xF0, 0x80, 0xF0, // 'E' at 0x46
        0xF0, 0x80, 0xF0, 0x80, 0x80  // 'F' at 0x4B
    )

    init {
        // 加载字体到内存
        FONT_SPRITES.forEachIndexed { index, v ->
            memory[index] = v.toByte()
        }
    }

    fun loadRom(romBytes: ByteArray) {
        // Load the rom at 0x200
        romBytes.copyInto(memory, 0x200)
    }

    override fun toString(): String {
        val vs = V.filter { it != 0 }.mapIndexed { ind, v -> "v$ind=$v" }
        val sp = SP.map { it }
        return "{Cpu pc=${Integer.toHexString(PC)} " +
                "i=${Integer.toHexString(I)} dt=${Integer.toHexString(DT)} " +
                "st=${Integer.toHexString(ST)} sp=${sp} $vs}"
    }
}