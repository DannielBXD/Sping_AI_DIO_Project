package com.br;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAiApiKeyTest {

    public static void main(String[] args) {

        String apiKey = System.getenv("OPENAI_API_KEY");

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/models"))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Status HTTP: " + response.statusCode());

            if (response.statusCode() == 200) {
                System.out.println("✅ API Key funcionando corretamente!");
            } else if (response.statusCode() == 401) {
                System.out.println("❌ API Key inválida.");
            } else if (response.statusCode() == 403) {
                System.out.println("❌ API Key sem permissão para acessar esse recurso.");
            } else if (response.statusCode() == 429) {
                System.out.println("⚠️ Limite de requisições ou quota atingida.");
            } else {
                System.out.println("⚠️ Erro inesperado.");
            }

            System.out.println("\nResposta da OpenAI:");
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println("Erro ao conectar com a API:");
            e.printStackTrace();
        }
    }
}