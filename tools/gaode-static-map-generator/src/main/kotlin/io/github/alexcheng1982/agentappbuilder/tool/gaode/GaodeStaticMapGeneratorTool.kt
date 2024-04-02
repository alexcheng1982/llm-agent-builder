package io.github.alexcheng1982.agentappbuilder.tool.gaode

import io.github.alexcheng1982.agentappbuilder.core.BaseConfigurableAgentToolFactory
import io.github.alexcheng1982.agentappbuilder.core.ConfigurableAgentTool
import io.github.alexcheng1982.gaode.StaticMap
import io.github.alexcheng1982.gaode.StaticMapGenerator
import io.github.alexcheng1982.gaode.param.*

data class Location(
    val latitude: Double,
    val longitude: Double,
    val name: String
)

data class Request(val locations: List<Location>)

data class Response(val url: String)

class GaodeStaticMapGeneratorTool(private val config: ToolConfig) :
    ConfigurableAgentTool<Request, Response, ToolConfig> {
    override fun name() = "gaodeStaticMap"

    override fun description() = "Generate URL of Gaode static maps"

    override fun apply(request: Request): Response {
        if (request.locations.isEmpty()) {
            return Response("")
        }
        val locations = request.locations + listOf(request.locations.first())
        val url = StaticMapGenerator.generate(
            StaticMap.builder()
                .key(getApiKey())
                .size(MapSize.MAXIMUM)
                .zoom(8)
                .scale(MapScaleMode.HIGH_RES)
                .paths(
                    Paths.builder()
                        .pathsGroups(
                            listOf(
                                PathsGroup.builder()
                                    .locations(
                                        locations.map { location ->
                                            GeoLocation.builder()
                                                .lat(location.latitude)
                                                .lng(location.longitude)
                                                .build()
                                        }
                                    )
                                    .build()
                            )
                        )
                        .build()
                )
                .build()
        )
        return Response(url)
    }

    private fun getApiKey(): String {
        return config.apiKey
    }
}

data class ToolConfig(
    val apiKey: String
)

class GaodeStaticMapGeneratorToolFactory :
    BaseConfigurableAgentToolFactory<ToolConfig, GaodeStaticMapGeneratorTool>({
        ToolConfig(
            System.getenv(
                "GAODE_API_KEY"
            )
        )
    }) {
    override fun create(config: ToolConfig): GaodeStaticMapGeneratorTool {
        return GaodeStaticMapGeneratorTool(config)
    }
}