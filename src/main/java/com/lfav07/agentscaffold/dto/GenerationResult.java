package com.lfav07.agentscaffold.dto;


public record GenerationResult(
        byte[] zip,
        String filename
) {}