package com.ll.standard.util

import java.io.File

object Util {
    object FileUtil {
        private fun getFile(filePath: String): File = File(filePath)

        fun touch(filePath: String) = set(filePath, "")

        fun exists(filePath: String): Boolean = getFile(filePath).exists()

        fun set(filePath: String, content: String) {
            val file = getFile(filePath)
            try {
                file.parentFile?.mkdirs()
                file.writeText(content)
            } catch (e: Exception) {
                throw RuntimeException("파일 쓰기 실패: $filePath", e)
            }
        }

        fun set(filePath: String, content: Int) = set(filePath, content.toString())

        fun delete(filePath: String): Boolean = getFile(filePath).deleteRecursively()

        fun get(filePath: String, defaultValue: String = ""): String {
            return try {
                getFile(filePath).readText()
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun getAsInt(filePath: String, defaultValue: Int = 0): Int {
            val value = get(filePath)
            if (value.isBlank()) return defaultValue

            return try {
                value.toInt()
            } catch (e: NumberFormatException) {
                defaultValue
            }
        }

        fun notExists(filePath: String): Boolean = !exists(filePath)

        fun mkdir(dirPath: String) {
            try {
                File(dirPath).mkdirs()
            } catch (e: Exception) {
                throw RuntimeException("디렉토리 생성 실패: $dirPath", e)
            }
        }

        fun rmdir(dirPath: String): Boolean = delete(dirPath)

        fun walkRegularFiles(dirPath: String, fileNameRegex: String): Sequence<File> {
            return try {
                File(dirPath).walkTopDown()
                    .filter { it.isFile }
                    .filter { it.name.matches(fileNameRegex.toRegex()) }
            } catch (e: Exception) {
                emptySequence()
            }
        }
    }

    object JsonUtil {
        fun toString(mapList: List<Map<String, Any?>>): String = buildString {
            appendLine("[")
            val indent = "    "

            mapList.forEach { map ->
                append(indent)
                append(toString(map).replace("\n", "\n$indent"))
                appendLine(",")
            }

            if (mapList.isNotEmpty()) {
                setLength(length - 2) // 마지막 콤마와 개행 제거
            }

            appendLine()
            append("]")
        }

        fun toString(map: Map<String, Any?>): String = buildString {
            appendLine("{")

            map.forEach { (key, value) ->
                append("    ")
                val formattedKey = "\"$key\""
                val formattedValue = when (value) {
                    is String -> "\"$value\""
                    else -> value
                }
                appendLine("$formattedKey: $formattedValue,")
            }

            if (map.isNotEmpty()) {
                setLength(length - 2) // 마지막 콤마와 개행 제거
            }

            appendLine()
            append("}")
        }

        fun toMap(jsonStr: String): Map<String, Any?> {
            val map = mutableMapOf<String, Any?>()

            val content = jsonStr.substring(1, jsonStr.length - 1)
            val fields = content.split(",\n    \"")

            for (field in fields) {
                val trimmedField = field.trim().removeSuffix(",")
                val parts = trimmedField.split("\": ", limit = 2)

                if (parts.size != 2) continue

                val key = parts[0].removePrefix("\"")
                val rawValue = parts[1]

                val value = when {
                    rawValue.startsWith("\"") && rawValue.endsWith("\"") -> {
                        rawValue.substring(1, rawValue.length - 1)
                    }
                    rawValue == "true" || rawValue == "false" -> rawValue.toBoolean()
                    rawValue.contains(".") -> rawValue.toDoubleOrNull() ?: rawValue
                    else -> rawValue.toIntOrNull() ?: rawValue
                }

                map[key] = value
            }

            return map
        }
    }
}