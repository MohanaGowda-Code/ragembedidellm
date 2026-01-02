package com.aiworld.ragembedidellm.service;


import com.aiworld.ragembedidellm.model.DocumentChunk;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final EmbeddingService embeddingService;
    private final LlmService llmService;

    public ChatService(EmbeddingService embeddingService, LlmService llmService) {
        this.embeddingService = embeddingService;
        this.llmService = llmService;
    }

    public String chat(String question) {

        List<DocumentChunk> chunks =
                embeddingService.semanticSearch(question, 3);
        String context = buildContext(chunks);
        return llmService.ask(context, question);
    }

    public String buildContext(List<DocumentChunk> chunks) {
        return chunks.stream()
                .map(DocumentChunk::getContent)
                .collect(Collectors.joining("\n\n"));
    }
}

