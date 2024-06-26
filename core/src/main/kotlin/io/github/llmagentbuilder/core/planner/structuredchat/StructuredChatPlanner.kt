package io.github.llmagentbuilder.core.planner.structuredchat

import io.github.llmagentbuilder.core.planner.LLMPlanner
import io.github.llmagentbuilder.core.planner.LLMPlannerFactory
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.core.io.ClassPathResource

object StructuredChatPlannerFactory : LLMPlannerFactory() {
    override fun defaultBuilder(): LLMPlanner.Builder {
        return LLMPlanner.Builder()
            .withUserPromptTemplate(PromptTemplate(ClassPathResource("prompts/structured-chat/user.st")))
            .withSystemPromptTemplate(PromptTemplate(ClassPathResource("prompts/structured-chat/system.st")))
            .withOutputParser(StructuredChatOutputParser.INSTANCE)
            .withSystemInstruction("Answer the following questions as best you can.")
    }

}