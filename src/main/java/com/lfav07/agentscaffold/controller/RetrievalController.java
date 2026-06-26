package com.lfav07.agentscaffold.controller;

import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class RetrievalController {

    private final RetrievalService retrievalService;

    @GetMapping("/agents")
    public ResponseEntity<Set<AgentItem>> getCoreAgents() {
        return ResponseEntity.ok(retrievalService.getCoreAgentsAvailable());
    }

    @GetMapping("/stacks")
    public ResponseEntity<Map<String, Set<StackItem>>> getAllStacks() {
        return ResponseEntity.ok(retrievalService.getAllStacksAvailable());
    }

    @GetMapping("/stacks/backend")
    public ResponseEntity<Set<StackItem>> getBackendStacks() {
        return ResponseEntity.ok(retrievalService.getBackendStacksAvailable());
    }

    @GetMapping("/stacks/frontend")
    public ResponseEntity<Set<StackItem>> getFrontendStacks() {
        return ResponseEntity.ok(retrievalService.getFrontendStacksAvailable());
    }

    @GetMapping("/presets")
    public ResponseEntity<Set<PresetItem>> getPresets() {
        return ResponseEntity.ok(retrievalService.getPresetsAvailable());
    }
}
