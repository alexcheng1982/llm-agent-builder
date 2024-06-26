package io.github.llmagentbuilder.core.planner.simple

import io.github.llmagentbuilder.core.planner.LLMPlanner
import io.github.llmagentbuilder.core.planner.LLMPlannerFactory
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.core.io.ClassPathResource

/**
 * Planning without feedback
 */
object SimplePlannerFactory : LLMPlannerFactory() {
    override fun defaultBuilder(): LLMPlanner.Builder {
        return LLMPlanner.Builder()
            .withUserPromptTemplate(PromptTemplate(ClassPathResource("prompts/simple/user.st")))
            .withSystemPromptTemplate(PromptTemplate(ClassPathResource("prompts/simple/system.st")))
            .withOutputParser(SimpleOutputParser.INSTANCE)
            .withSystemInstruction("You are a helpful assistant.")
    }
}