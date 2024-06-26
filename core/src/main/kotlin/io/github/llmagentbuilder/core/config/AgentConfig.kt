package io.github.llmagentbuilder.core.config

import io.github.llmagentbuilder.core.Planner
import io.github.llmagentbuilder.core.chatmemory.ChatMemoryStore
import io.github.llmagentbuilder.core.planner.ChatHistoryCustomizer
import io.github.llmagentbuilder.core.planner.react.ReActPlannerFactory
import io.github.llmagentbuilder.core.planner.reactjson.ReActJsonPlannerFactory
import io.github.llmagentbuilder.core.planner.simple.SimplePlannerFactory
import io.github.llmagentbuilder.core.planner.structuredchat.StructuredChatPlannerFactory
import io.github.llmagentbuilder.core.tool.AgentToolsProvider
import io.github.llmagentbuilder.core.tool.AutoDiscoveredAgentToolsProvider
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.observation.ObservationRegistry
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.ChatOptions

enum class PlannerType {
    ReAct {
        override fun create(agentConfig: AgentConfig): Planner {
            return ReActPlannerFactory.create(agentConfig)
        }
    },
    ReActJson {
        override fun create(agentConfig: AgentConfig): Planner {
            return ReActJsonPlannerFactory.create(agentConfig)
        }
    },
    StructuredChat {
        override fun create(agentConfig: AgentConfig): Planner {
            return StructuredChatPlannerFactory.create(agentConfig)
        }
    },
    Simple {
        override fun create(agentConfig: AgentConfig): Planner {
            return SimplePlannerFactory.create(agentConfig)
        }
    };

    abstract fun create(agentConfig: AgentConfig): Planner
}

data class MetadataConfig(
    val name: String = "ChatAgent",
    val description: String = "A conversational chat agent",
    val id: String? = null,
    val usageInstruction: String? = null,
)

data class LLMConfig(
    val chatModel: ChatModel,
    val chatOptions: ChatOptions,
)

data class PlannerConfig(
    val plannerType: PlannerType = PlannerType.Simple,
    val systemInstruction: String? = null,
)

data class ToolsConfig(
    val agentToolsProvider: AgentToolsProvider? = AutoDiscoveredAgentToolsProvider,
)

data class MemoryConfig(
    val chatMemoryStore: ChatMemoryStore? = null,
    val chatHistoryCustomizer: ChatHistoryCustomizer? = null,
)

data class ObservationConfig(
    val observationRegistry: ObservationRegistry? = null,
    val meterRegistry: MeterRegistry? = null,
)

class AgentConfig(
    val llmConfig: LLMConfig,
    val metadataConfig: MetadataConfig? = null,
    val plannerConfig: PlannerConfig? = null,
    val toolsConfig: ToolsConfig? = null,
    val memoryConfig: MemoryConfig? = null,
    val observationConfig: ObservationConfig? = null,
) {
    fun metadataConfig() = metadataConfig ?: MetadataConfig()
    fun plannerConfig() = plannerConfig ?: PlannerConfig()
    fun toolsConfig() = toolsConfig ?: ToolsConfig()
    fun memoryConfig() = memoryConfig ?: MemoryConfig()
    fun observationConfig() = observationConfig ?: ObservationConfig()
}