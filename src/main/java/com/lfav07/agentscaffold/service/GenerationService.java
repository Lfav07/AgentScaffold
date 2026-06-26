package com.lfav07.agentscaffold.service;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;

public interface GenerationService {
    GenerationResult generate(GenerationRequest request);
}
