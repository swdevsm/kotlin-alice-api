package ru.swdevsm.alice.jackson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assert
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.swdevsm.Resources.asText

class AliceRequestTest {
  @Test
  fun serialize() {
    assertThat(
      jackson.writeValueAsString(
        AliceRequest(
          request = SimpleUtterance(
            command = "command",
            originalUtterance = "utterance",
            markup = null,
            nlu = null,
          ),
          meta = null,
          session = null,
          version = null,
        ),
      ),
    ).isJsonEqualTo("request.json".asText())
  }
}

private val jackson = jacksonObjectMapper()

private fun String.toJsonNode(): JsonNode = jackson.readValue(this)

fun Assert<*, *>.isJsonEqualTo(expected: String) {
  asString().satisfies({ assertThat(it.toJsonNode()).isEqualTo(expected.toJsonNode()) })
}
