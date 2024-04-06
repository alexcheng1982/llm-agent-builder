package io.github.alexcheng1982.agentappbuilder.core.planner.reactjson

import io.github.alexcheng1982.agentappbuilder.core.planner.LLMPlanner
import io.github.alexcheng1982.agentappbuilder.core.planner.LLMPlannerFactory
import io.github.alexcheng1982.agentappbuilder.core.tool.AgentToolsProvider
import io.github.alexcheng1982.agentappbuilder.core.tool.AutoDiscoveredAgentToolsProvider
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.observation.ObservationRegistry
import org.springframework.ai.chat.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.core.io.ClassPathResource

object ReActJsonPlannerFactory : LLMPlannerFactory() {
    override fun defaultBuilder(): LLMPlanner.Builder {
        return LLMPlanner.Builder()
            .withUserPromptTemplate(PromptTemplate(ClassPathResource("prompts/react-json/user.st")))
            .withSystemPromptTemplate(PromptTemplate(ClassPathResource("prompts/react-json/system.st")))
            .withOutputParser(ReActJsonOutputParser.INSTANCE)
            .withSystemInstruction("Answer the following questions as best you can.")
    }
}