package com.lfav07.agentscaffold.integration;

import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.service.GenerationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.lfav07.agentscaffold.controller.GenerationController;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenerationController.class)
@ActiveProfiles("test")
class GenerationApiIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenerationService generationService;

    @Test
    void scaffold_shouldReturn200WithZipAttachment_forValidRequest() throws Exception {
        when(generationService.generate(any())).thenReturn(
                new GenerationResult(new byte[]{1, 2, 3}, "test-agents.zip")
        );

        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "enterprise-spring",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void scaffold_responseContentTypeShouldBeApplicationZip() throws Exception {
        when(generationService.generate(any())).thenReturn(
                new GenerationResult(new byte[]{1, 2, 3}, "test-agents.zip")
        );

        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "enterprise-spring",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(content().contentType("application/zip"));
    }

    @Test
    void scaffold_responseContentDispositionShouldIncludeFilename() throws Exception {
        when(generationService.generate(any())).thenReturn(
                new GenerationResult(new byte[]{1, 2, 3}, "test-agents.zip")
        );

        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "enterprise-spring",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test-agents.zip\""));
    }
}
