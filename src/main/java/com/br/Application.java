package com.br;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	@Bean
	ChatClient chatChatClient(ChatClient.Builder builder) {
		return builder.build();

	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
