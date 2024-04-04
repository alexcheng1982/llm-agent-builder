package io.github.alexcheng1982.agentappbuilder.core.tool

import java.util.function.Supplier

interface AgentToolFactory<out T : AgentTool<*, *>> {
    fun create(): T
}

interface ConfigurableAgentToolFactory<CONFIG, out T : ConfigurableAgentTool<*, *, CONFIG>> :
    AgentToolFactory<T> {
    fun create(config: CONFIG): T
}

abstract class BaseConfigurableAgentToolFactory<out T : ConfigurableAgentTool<*, *, CONFIG>, CONFIG>(
    private val configProvider: Supplier<CONFIG>
) : ConfigurableAgentToolFactory<CONFIG, T> {
    override fun create(): T {
        return create(configProvider.get())
    }
}