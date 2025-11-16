package ru.swdevsm

import java.io.FileNotFoundException
import java.nio.charset.Charset
import kotlin.io.readText
import kotlin.io.reader
import kotlin.jvm.javaClass
import kotlin.text.Charsets.UTF_8

object Resources {
  fun String.asText(charset: Charset = UTF_8): String =
    Resources.javaClass.classLoader.getResourceAsStream(this)?.reader(charset)?.readText() ?: throw FileNotFoundException(this)
}
