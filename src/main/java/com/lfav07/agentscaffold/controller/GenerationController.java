package com.lfav07.agentscaffold.controller;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.service.GenerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class GenerationController {
    private final GenerationService generationService;

    /**
     * Generates a project ZIP from the given request and returns it as a downloadable resource.
     *
     * @param request the generation request with project configuration.
     * @return a response entity containing the ZIP file as a byte array resource.
     */
    @PostMapping("/scaffold")
    public ResponseEntity<Resource> scaffold(@Valid @RequestBody GenerationRequest request){
        log.info("Generation request received — project: {}, preset: {}, backend: {}, frontend: {}",
                request.projectName(), request.preset(), request.backendStack(), request.frontendStack());
        GenerationResult result =  generationService.generate(request);
        byte[] zip = result.zip();
        ByteArrayResource resource = new ByteArrayResource(zip);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/zip"))
                .contentLength(zip.length)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(result.filename())
                                .build()
                                .toString()
                )
                .body(resource);
    }

}
