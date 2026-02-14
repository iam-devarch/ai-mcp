package com.devarch.mcp;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.ai.chat.model.ChatModel;
import java.util.List;

@SpringBootApplication
public class McpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel, List<McpSyncClient> mcpClients) {
        SyncMcpToolCallbackProvider mcpTools = new SyncMcpToolCallbackProvider(mcpClients);

        return ChatClient.builder(chatModel)
                .defaultToolCallbacks(mcpTools.getToolCallbacks())
                .build();
    }

    @Bean
    public CommandLineRunner runner(ChatClient chatClient) {
        return args -> {
            String response = chatClient.prompt("What is the weather in San Francisco?")
                    .call()
                    .content();
            System.out.println("Response: " + response);
        };
    }
}
