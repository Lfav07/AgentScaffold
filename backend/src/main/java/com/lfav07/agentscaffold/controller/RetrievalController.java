package com.lfav07.agentscaffold.controller;

import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.service.RetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Tag(name = "Retrieval", description = "Lookup endpoints for available agents, stacks, and presets")
public class RetrievalController {

    private final RetrievalService retrievalService;

    @Operation(summary = "List core agent types", description = "Returns all available core agent types that can be used in a scaffold generation.")
    @ApiResponse(responseCode = "200", description = "Set of available core agent types",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AgentItem.class))))
    @GetMapping("/agents")
    public ResponseEntity<Set<AgentItem>> getCoreAgents() {
        Set<AgentItem> agents = retrievalService.getCoreAgentsAvailable();
        log.info("Listing {} agents", agents.size());
        return ResponseEntity.ok(agents);
    }

    @Operation(summary = "List all stacks", description = "Returns all available technology stacks grouped by category (e.g. backend, frontend).")
    @ApiResponse(responseCode = "200", description = "Map of stack categories to sets of stack items")
    @GetMapping("/stacks")
    public ResponseEntity<Map<String, Set<StackItem>>> getAllStacks() {
        Map<String, Set<StackItem>> stacks = retrievalService.getAllStacksAvailable();
        int total = stacks.values().stream().mapToInt(Set::size).sum();
        log.info("Listing {} stacks", total);
        return ResponseEntity.ok(stacks);
    }

    @Operation(summary = "List backend stacks", description = "Returns all available backend technology stacks.")
    @ApiResponse(responseCode = "200", description = "Set of available backend stack items",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StackItem.class))))
    @GetMapping("/stacks/backend")
    public ResponseEntity<Set<StackItem>> getBackendStacks() {
        Set<StackItem> stacks = retrievalService.getBackendStacksAvailable();
        log.info("Listing {} backend stacks", stacks.size());
        return ResponseEntity.ok(stacks);
    }

    @Operation(summary = "List frontend stacks", description = "Returns all available frontend technology stacks.")
    @ApiResponse(responseCode = "200", description = "Set of available frontend stack items",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StackItem.class))))
    @GetMapping("/stacks/frontend")
    public ResponseEntity<Set<StackItem>> getFrontendStacks() {
        Set<StackItem> stacks = retrievalService.getFrontendStacksAvailable();
        log.info("Listing {} frontend stacks", stacks.size());
        return ResponseEntity.ok(stacks);
    }

    @Operation(summary = "List generation presets", description = "Returns all available generation presets.")
    @ApiResponse(responseCode = "200", description = "Set of available preset items",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PresetItem.class))))
    @GetMapping("/presets")
    public ResponseEntity<Set<PresetItem>> getPresets() {
        Set<PresetItem> presets = retrievalService.getPresetsAvailable();
        log.info("Listing {} presets", presets.size());
        return ResponseEntity.ok(presets);
    }
}
