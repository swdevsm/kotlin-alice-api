package ru.swdevsm.alice.jackson

import com.fasterxml.jackson.annotation.JsonProperty

data class AliceResponse(
  val response: Response,
  @param:JsonProperty("session_state")
  val sessionState: Map<String, Any> = emptyMap(),
  @param:JsonProperty("user_state_update")
  val userStateUpdate: Map<String, Any> = emptyMap(),
  @param:JsonProperty("application_state")
  val applicationState: Map<String, Any> = emptyMap(),
  val analytics: Analytics? = null,
  val version: String?,
)

data class Response(
  val text: String,
  val tts: String? = null,
  val card: Card? = null,
  val buttons: List<Button> = emptyList(),
  @param:JsonProperty("end_session")
  val endSession: Boolean = false,
  val directives: Map<String, Any> = emptyMap(),
)

data class Card(
  val type: String, //todo: different types
)

data class Button(
  val title: String,
  val url: String? = null,
  val payload: String? = null,
  val hide: Boolean = false,
)

data class Analytics(
  val events: List<AnalyticsEvent> = emptyList(),
)

data class AnalyticsEvent(
  val name: String,
  val value: Map<String, Any> = emptyMap(),
)
