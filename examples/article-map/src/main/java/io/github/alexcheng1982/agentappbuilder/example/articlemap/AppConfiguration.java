package io.github.alexcheng1982.agentappbuilder.example.articlemap;

import cc.vividcode.ai.agent.dashscope.DashscopeChatClient;
import cc.vividcode.ai.agent.dashscope.DashscopeChatOptions;
import cc.vividcode.ai.agent.dashscope.api.DashscopeApi;
import cc.vividcode.ai.agent.dashscope.api.DashscopeModelName;
import io.github.alexcheng1982.agentappbuilder.spring.AgentToolFunctionCallbackContext;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {

  @Bean
  @Primary
  public ChatClient chatClient(
      FunctionCallbackContext functionCallbackContext) {
    return new DashscopeChatClient(
        new DashscopeApi(),
        DashscopeChatOptions.builder()
            .withModel(DashscopeModelName.QWEN_MAX)
            .withTemperature(0.2f)
            .build(),
        functionCallbackContext
    );
  }

  @Bean
  public FunctionCallbackContext springAiFunctionManager(
      ApplicationContext context) {
    var manager = new AgentToolFunctionCallbackContext();
    manager.setApplicationContext(context);
    return manager;
  }
}