package com.lfav07.agentscaffold.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RetrievalApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCoreAgents_shouldReturn200WithAgentList() throws Exception {
        mockMvc.perform(get("/api/v1/agents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllStacks_shouldReturn200WithCategorizedStacks() throws Exception {
        mockMvc.perform(get("/api/v1/stacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.backend").isArray())
                .andExpect(jsonPath("$.frontend").isArray());
    }

    @Test
    void getBackendStacks_shouldReturn200WithBackendStacks() throws Exception {
        mockMvc.perform(get("/api/v1/stacks/backend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getFrontendStacks_shouldReturn200WithFrontendStacks() throws Exception {
        mockMvc.perform(get("/api/v1/stacks/frontend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getPresets_shouldReturn200WithPresetList() throws Exception {
        mockMvc.perform(get("/api/v1/presets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
