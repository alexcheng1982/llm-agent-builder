package io.github.llmagentbuilder.core.planner.planner.reactjson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.llmagentbuilder.core.AgentAction
import io.github.llmagentbuilder.core.AgentFinish
import io.github.llmagentbuilder.core.planner.planner.JsonParser
import io.github.llmagentbuilder.core.planner.planner.OutputParser
import io.github.llmagentbuilder.core.planner.planner.OutputParserException
import io.github.llmagentbuilder.core.planner.planner.ParseResult

class ReActJsonOutputParser : OutputParser {
    private val finalAnswerAction = "Final Answer:"

    private val objectMapper =
        ObjectMapper().registerModule(KotlinModule.Builder().build())

    override fun parse(text: String): ParseResult {
        val includeAnswer = text.contains(finalAnswerAction)
        if (includeAnswer) {
            val output = text.split(finalAnswerAction).last().trim()
            return ParseResult.finishWithOutputAndLog(output, text)
        }
        val response = JsonParser.parse(text)
        if (response != null) {
            val actionInput = response["action_input"]?.let {
                objectMapper.writeValueAsString(it)
            } ?: "{}"
            if (response["action"] == "Final Answer") {
                return ParseResult.finish(
                    AgentFinish(
                        mapOf(
                            "output" to actionInput
                        ), text
                    )
                )
            } else if (response["action"] != null) {
                return response["action"].let {
                    ParseResult.action(
                        AgentAction(
                            it.toString(),
                            actionInput,
                            text
                        )
                    )
                }
            }
        }
        throw OutputParserException(
            "Could not parse LLM output: $text",
            text,
            text,
            true
        )
    }

    companion object {
        val INSTANCE = ReActJsonOutputParser()
    }
}