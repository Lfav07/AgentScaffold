package com.lfav07.agentscaffold.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ErrorHandlingIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void scaffold_shouldReturn400_whenRequestBodyMissing() throws Exception {
        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void scaffold_shouldReturn400_whenProjectNameBlank() throws Exception {
        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "enterprise-spring",
                                    "projectName": "",
                                    "backendStack": "java-spring",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void scaffold_shouldReturn400_whenInvalidEnumValue() throws Exception {
        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "nonexistent-preset",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void scaffold_shouldReturn400_whenFullstackPresetMissingBackendStack() throws Exception {
        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "enterprise-fullstack",
                                    "projectName": "MyProject",
                                    "frontendStack": "typescript-react"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void scaffold_shouldReturn400_whenFullstackPresetMissingFrontendStack() throws Exception {
        mockMvc.perform(post("/api/v1/scaffold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "preset": "enterprise-fullstack",
                                    "projectName": "MyProject",
                                    "backendStack": "java-spring"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void getUnknownRoute_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/v1/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
