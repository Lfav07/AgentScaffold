package com.lfav07.agentscaffold.unit.model;

import com.lfav07.agentscaffold.exception.InvalidAgentTypeException;
import com.lfav07.agentscaffold.exception.InvalidPresetException;
import com.lfav07.agentscaffold.exception.InvalidStackException;
import com.lfav07.agentscaffold.model.agent.AdditionalAgentType;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import com.lfav07.agentscaffold.model.stack.GeneralStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnumTypeResolutionTest {

    @ParameterizedTest
    @CsvSource({
        "backend-architect, BACKEND_ARCHITECT",
        "backend-architect-reviewer, BACKEND_ARCHITECT_REVIEWER",
        "backend-implementer, BACKEND_IMPLEMENTER",
        "backend-tester, BACKEND_TESTER",
        "frontend-architect, FRONTEND_ARCHITECT",
        "frontend-architect-reviewer, FRONTEND_ARCHITECT_REVIEWER",
        "frontend-implementer, FRONTEND_IMPLEMENTER",
        "frontend-tester, FRONTEND_TESTER",
        "final-reviewer, FINAL_REVIEWER"
    })
    void coreAgentType_fromValue_shouldReturnEnum_forValidKebabCase(String input, CoreAgentType expected) {
        assertThat(CoreAgentType.fromValue(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "BACKEND_ARCHITECT, BACKEND_ARCHITECT",
        "BACKEND_IMPLEMENTER, BACKEND_IMPLEMENTER",
        "FRONTEND_ARCHITECT, FRONTEND_ARCHITECT",
        "FINAL_REVIEWER, FINAL_REVIEWER"
    })
    void coreAgentType_fromValue_shouldReturnEnum_forValidUpperCase(String input, CoreAgentType expected) {
        assertThat(CoreAgentType.fromValue(input)).isEqualTo(expected);
    }

    @Test
    void coreAgentType_fromValue_shouldThrowInvalidAgentTypeException_forNull() {
        assertThatThrownBy(() -> CoreAgentType.fromValue(null))
                .isInstanceOf(InvalidAgentTypeException.class)
                .hasMessageContaining("cannot be null");
    }

    @Test
    void coreAgentType_fromValue_shouldThrowInvalidAgentTypeException_forInvalidValue() {
        assertThatThrownBy(() -> CoreAgentType.fromValue("nonexistent-agent"))
                .isInstanceOf(InvalidAgentTypeException.class)
                .hasMessageContaining("Invalid Core Agent Type");
    }

    @Test
    void additionalAgentType_fromValue_shouldReturnEnum_forValidValue() {
        assertThat(AdditionalAgentType.fromValue("sql-agent"))
                .isEqualTo(AdditionalAgentType.SQL_AGENT);
    }

    @Test
    void additionalAgentType_fromValue_shouldThrowInvalidAgentTypeException_forNull() {
        assertThatThrownBy(() -> AdditionalAgentType.fromValue(null))
                .isInstanceOf(InvalidAgentTypeException.class)
                .hasMessageContaining("cannot be null");
    }

    @Test
    void additionalAgentType_fromValue_shouldThrowInvalidAgentTypeException_forInvalidValue() {
        assertThatThrownBy(() -> AdditionalAgentType.fromValue("nonexistent"))
                .isInstanceOf(InvalidAgentTypeException.class)
                .hasMessageContaining("Invalid Additional Agent Type");
    }

    @ParameterizedTest
    @CsvSource({
        "enterprise-fullstack, ENTERPRISE_FULLSTACK",
        "enterprise-spring, ENTERPRISE_SPRING"
    })
    void generationPreset_fromValue_shouldReturnEnum_forValidValue(String input, GenerationPreset expected) {
        assertThat(GenerationPreset.fromValue(input)).isEqualTo(expected);
    }

    @Test
    void generationPreset_fromValue_shouldThrowInvalidPresetException_forNull() {
        assertThatThrownBy(() -> GenerationPreset.fromValue(null))
                .isInstanceOf(InvalidPresetException.class)
                .hasMessageContaining("cannot be null");
    }

    @Test
    void generationPreset_fromValue_shouldThrowInvalidPresetException_forInvalidValue() {
        assertThatThrownBy(() -> GenerationPreset.fromValue("unknown-preset"))
                .isInstanceOf(InvalidPresetException.class)
                .hasMessageContaining("Invalid Generation Preset");
    }

    @Test
    void backendStack_fromValue_shouldReturnEnum_forValidValue() {
        assertThat(BackendStack.fromValue("java-spring"))
                .isEqualTo(BackendStack.JAVA_SPRING);
    }

    @Test
    void backendStack_fromValue_shouldThrowInvalidStackException_forNull() {
        assertThatThrownBy(() -> BackendStack.fromValue(null))
                .isInstanceOf(InvalidStackException.class)
                .hasMessageContaining("cannot be null");
    }

    @Test
    void frontendStack_fromValue_shouldReturnEnum_forValidValue() {
        assertThat(FrontendStack.fromValue("typescript-react"))
                .isEqualTo(FrontendStack.TYPESCRIPT_REACT);
    }

    @Test
    void frontendStack_fromValue_shouldThrowInvalidStackException_forNull() {
        assertThatThrownBy(() -> FrontendStack.fromValue(null))
                .isInstanceOf(InvalidStackException.class)
                .hasMessageContaining("cannot be null");
    }

    @Test
    void generalStack_fromValue_shouldReturnEnum_forValidValue() {
        assertThat(GeneralStack.fromValue("general"))
                .isEqualTo(GeneralStack.GENERAL);
    }

    @Test
    void generalStack_fromValue_shouldThrowInvalidStackException_forNull() {
        assertThatThrownBy(() -> GeneralStack.fromValue(null))
                .isInstanceOf(InvalidStackException.class)
                .hasMessageContaining("cannot be null");
    }
}
