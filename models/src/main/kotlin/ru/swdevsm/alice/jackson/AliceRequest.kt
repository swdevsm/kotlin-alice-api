package ru.swdevsm.alice.jackson

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class AliceRequest(
  val meta: Meta?,
  val request: Request,
  val session: Session?,
  val state: State? = null,
  val version: String?,
)

data class Meta(
  val locale: String,
  val timezone: String,
  @param:JsonProperty("client_id")
  val clientId: String,
  val interfaces: Interfaces,
)

data class Interfaces(
  val screen: Map<String, Any> = emptyMap(),
  @param:JsonProperty("account_linking")
  val accountLinking: Map<String, Any> = emptyMap(),
  @param:JsonProperty("audio_player")
  val audioPlayer: Map<String, Any> = emptyMap(),
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  JsonSubTypes.Type(value = SimpleUtterance::class, name = "SimpleUtterance"),
)
sealed class Request(
  val type: String, //todo: different types
)

data class SimpleUtterance(
  val command: String,
  @param:JsonProperty("original_utterance")
  val originalUtterance: String,
  val markup: Markup?,
  val payload: Map<String, Any>? = emptyMap(),
  val nlu: Nlu?,
) : Request("SimpleUtterance")

data class ButtonPressed(
  val markup: Markup?,
  val payload: String?,
  val nlu: Nlu?,
  val tokens: Map<String, Any>? = emptyMap(),
) : Request("ButtonPressed")

data class Markup(
  @param:JsonProperty("dangerous_context")
  val dangerousContext: Boolean,
)

data class Nlu(
  val tokens: List<String>,
  val entities: List<Entity>,
  val intents: Map<String, Any> = emptyMap(),
)

data class Entity(
  val tokens: TokenRange,
  val type: String,
  val value: Any,
)

data class TokenRange(
  val start: Int,
  val end: Int,
)

data class Session(
  @param:JsonProperty("session_id")
  val sessionId: String,
  @param:JsonProperty("message_id")
  val messageId: Int,
  @param:JsonProperty("skill_id")
  val skillId: String,
  @param:JsonProperty("user_id")
  @Deprecated("https://yandex.ru/dev/dialogs/alice/doc/ru/request#session-desc")
  val userId: String?,
  val user: User?,
  val application: Application,
  val new: Boolean,
)

data class User(
  @param:JsonProperty("user_id")
  val userId: String,
  @param:JsonProperty("access_token")
  val accessToken: String?,
)

data class Application(
  @param:JsonProperty("application_id")
  val applicationId: String,
)

data class State(
  val session: Map<String, Any> = emptyMap(),
  val user: Map<String, Any> = emptyMap(),
  val application: Map<String, Any> = emptyMap(),
)
