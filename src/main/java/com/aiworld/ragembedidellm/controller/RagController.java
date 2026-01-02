package com.aiworld.ragembedidellm.controller;

import com.aiworld.ragembedidellm.model.*;
import com.aiworld.ragembedidellm.service.ChatService;
import com.aiworld.ragembedidellm.service.ChunkingService;
import com.aiworld.ragembedidellm.service.DocumentService;
import com.aiworld.ragembedidellm.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/rag")
public class RagController {


    @Autowired
    private DocumentService documentService;

    @Autowired
    private ChunkingService chunkingService;

    @Autowired
    EmbeddingService embeddingService;

    @Autowired
    ChatService chatService;

    @PostMapping("/upload")
    public List<DocumentChunk> upload(@RequestParam MultipartFile file) {
        String text = documentService.extractText(file);       // STEP-2
        List<DocumentChunk> chunks = chunkingService.chunkText(text); // STEP-3
        return embeddingService.generateSingleEmbedding(chunks);   // STEP-4
    }

    @PostMapping("/search")
    public List<SearchResponse> search(@RequestBody SearchRequest request) {
        return embeddingService.semanticSearch(
                        request.getQuery(),
                        request.getTopK()
                ).stream()
                .map(c -> new SearchResponse(c.getIndex(), c.getContent()))
                .toList();
    }


    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        return chatService.chat(request.getQuestion());
    }
}
