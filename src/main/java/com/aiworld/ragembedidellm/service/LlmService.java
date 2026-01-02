package com.aiworld.ragembedidellm.service;

import org.springframework.stereotype.Service;

@Service
public class LlmService {

    public String ask(String context, String question) {

        String prompt = """
        You are a helpful assistant.
        Answer the question strictly using the context below.

        Context:
        %s

        Question:
        %s
        """.formatted(context, question);

        // 🔜 Later this will call OpenAI / Ollama / Azure
        return "LLM response will come here";
    }
}

