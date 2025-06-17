package com.hotelchatbot.embeddings;

import java.io.IOException;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class EmbeddingService {

    @Value("${spring.ai.openai.api-key}")
    private String openAIApiKey;

    @Value("${openai.api.embeddingModel}")
    private String openAIEmbeddingModel;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClients.createDefault();

    public float[] getEmbedding(String input) throws IOException {

        // Preprocess the input String
        // remove stopwords and make all words lowercase
        String embeddingString = StopWords.removeStopWords(input);
        
        // Optional check, if the embedding string is too long, truncate it.
        if (embeddingString.length() > 10000) {
            embeddingString = embeddingString.substring(0, 10000);
            System.out.println("truncated string!");
        }

        // Construct the request headers
        HttpPost request = new HttpPost("https://api.openai.com/v1/embeddings");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAIApiKey);

        // Construct the payload/request body
        ObjectNode json = objectMapper.createObjectNode();
        json.put("input", embeddingString);
        json.put("model", openAIEmbeddingModel);

        // Place the payload/request body into the request (e.g. finish building the request)
        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        // Send to OpenAI
        try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request)) {
            if (response.getCode() != 200) {
                throw new RuntimeException("Failed to get embedding: " + response.getCode());
            }

            JsonNode jsonResponse = objectMapper.readTree(response.getEntity().getContent());
            JsonNode vector = jsonResponse.get("data").get(0).get("embedding");

            float[] embedding = new float[vector.size()];
            for (int i = 0; i < vector.size(); i++) {
                embedding[i] = (float) vector.get(i).asDouble();
            }

            return embedding;
        }
    }
}