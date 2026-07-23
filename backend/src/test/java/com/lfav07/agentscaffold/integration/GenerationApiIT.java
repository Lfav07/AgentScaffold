package com.lfav07.agentscaffold.integration;

import com.lfav07.agentscaffold.controller.GenerationController;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.dto.validation.GenerationRequestValidator;
import com.lfav07.agentscaffold.fixture.TestEntities;
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
                                    "presetKey": "enterprise-spring",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring"
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
                                    "presetKey": "enterprise-spring",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring"
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
                                    "presetKey": "enterprise-spring",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring"
                                }
                                """))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test-agents.zip\""));
    }

    @Test
    void scaffold_shouldReturn400_whenMissingRequiredFields() throws Exception {
        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "presetKey": "enterprise-spring"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void scaffold_shouldReturn400_whenBackendStackMissing() throws Exception {
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(Set.of(
                TestEntities.backendArchitect(),
                TestEntities.frontendArchitect()
        ));

        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "presetKey": "enterprise-fullstack",
                                    "projectName": "MyProject",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
