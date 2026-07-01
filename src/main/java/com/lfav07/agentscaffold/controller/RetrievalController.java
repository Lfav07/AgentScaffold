package com.lfav07.agentscaffold.controller;

import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class RetrievalController {

    private final RetrievalService retrievalService;

    @GetMapping("/agents")
    public ResponseEntity<Set<AgentItem>> getCoreAgents() {
        Set<AgentItem> agents = retrievalService.getCoreAgentsAvailable();
        log.info("Listing {} agents", agents.size());
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/stacks")
    public ResponseEntity<Map<String, Set<StackItem>>> getAllStacks() {
        Map<String, Set<StackItem>> stacks = retrievalService.getAllStacksAvailable();
        int total = stacks.values().stream().mapToInt(Set::size).sum();
        log.info("Listing {} stacks", total);
        return ResponseEntity.ok(stacks);
    }

    @GetMapping("/stacks/backend")
    public ResponseEntity<Set<StackItem>> getBackendStacks() {
        Set<StackItem> stacks = retrievalService.getBackendStacksAvailable();
        log.info("Listing {} backend stacks", stacks.size());
        return ResponseEntity.ok(stacks);
    }

    @GetMapping("/stacks/frontend")
    public ResponseEntity<Set<StackItem>> getFrontendStacks() {
        Set<StackItem> stacks = retrievalService.getFrontendStacksAvailable();
        log.info("Listing {} frontend stacks", stacks.size());
        return ResponseEntity.ok(stacks);
    }

    @GetMapping("/presets")
    public ResponseEntity<Set<PresetItem>> getPresets() {
        Set<PresetItem> presets = retrievalService.getPresetsAvailable();
        log.info("Listing {} presets", presets.size());
        return ResponseEntity.ok(presets);
    }
}
