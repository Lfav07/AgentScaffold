package com.lfav07.agentscaffold.integration;

import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.dto.validation.GenerationRequestValidator;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import com.lfav07.agentscaffold.service.GenerationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.lfav07.agentscaffold.controller.GenerationController;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenerationController.class)
@Import(GenerationRequestValidator.class)
@ActiveProfiles("test")
class GenerationApiIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenerationService generationService;

    @MockitoBean
    private PresetAgentResolver presetAgentResolver;

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
    void scaffold_shouldReturn200_whenReactReadyPresetWithoutBackendStack() throws Exception {
        when(presetAgentResolver.resolve(GenerationPreset.REACT_READY)).thenReturn(
                Set.of(
                        com.lfav07.agentscaffold.model.agent.CoreAgentType.FRONTEND_ARCHITECT,
                        com.lfav07.agentscaffold.model.agent.CoreAgentType.FRONTEND_IMPLEMENTER,
                        com.lfav07.agentscaffold.model.agent.CoreAgentType.FRONTEND_TESTER
                )
        );
        when(generationService.generate(any())).thenReturn(
                new GenerationResult(new byte[]{1, 2, 3}, "test-agents.zip")
        );

        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "react-ready",
                                    "projectName": "MyProject",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(status().isOk());
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
