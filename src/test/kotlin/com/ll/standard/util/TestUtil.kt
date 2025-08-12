package com.ll.standard.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.PrintStream
import java.io.UncheckedIOException

class TestUtil {
    companion object {
        val ORIGINAL_OUT: PrintStream = System.out

        fun setInToByteArray(input: String) {
            val inputStream = input.byteInputStream()
            System.setIn(inputStream)
        }

        fun setOutToByteArray(): ByteArrayOutputStream {
            val output = ByteArrayOutputStream()
            System.setOut(PrintStream(output))
            return output
        }

        fun clearSetOutToByteArray(output: ByteArrayOutputStream) {
            System.setOut(ORIGINAL_OUT)

            try {
                output.close()
            } catch (e: IOException) {
                throw UncheckedIOException(e);
            }
        }
    }
}