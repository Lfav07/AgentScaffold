package com.lfav07.agentscaffold.unit.dto;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.validation.GenerationRequestValidator;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerationRequestValidatorTest {

    @Mock
    private PresetAgentResolver presetAgentResolver;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder;

    private GenerationRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new GenerationRequestValidator(presetAgentResolver);
    }

    @Test
    void isValid_shouldPass_whenFrontendOnlyPresetWithBackendStackNull() {
        when(presetAgentResolver.resolve(GenerationPreset.REACT_READY)).thenReturn(Set.of(
                CoreAgentType.FRONTEND_ARCHITECT,
                CoreAgentType.FRONTEND_IMPLEMENTER,
                CoreAgentType.FRONTEND_TESTER
        ));

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.REACT_READY,
                "Test",
                null,
                FrontendStack.TYPESCRIPT_REACT,
                null,
                null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isTrue();
    }

    @Test
    void isValid_shouldPass_whenBackendOnlyPresetWithFrontendStackNull() {
        when(presetAgentResolver.resolve(GenerationPreset.ENTERPRISE_SPRING)).thenReturn(Set.of(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.BACKEND_IMPLEMENTER,
                CoreAgentType.BACKEND_TESTER
        ));

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.ENTERPRISE_SPRING,
                "Test",
                BackendStack.JAVA_SPRING,
                null,
                null,
                null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isTrue();
    }

    @Test
    void isValid_shouldFail_whenFullstackPresetMissingBackendStack() {
        when(presetAgentResolver.resolve(GenerationPreset.ENTERPRISE_FULLSTACK)).thenReturn(Set.of(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.FRONTEND_ARCHITECT
        ));
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(any())).thenReturn(nodeBuilder);

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.ENTERPRISE_FULLSTACK,
                "Test",
                null,
                FrontendStack.TYPESCRIPT_REACT,
                null,
                null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isFalse();
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(contains("Backend stack is required"));
    }

    @Test
    void isValid_shouldFail_whenFullstackPresetMissingFrontendStack() {
        when(presetAgentResolver.resolve(GenerationPreset.ENTERPRISE_FULLSTACK)).thenReturn(Set.of(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.FRONTEND_ARCHITECT
        ));
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(any())).thenReturn(nodeBuilder);

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.ENTERPRISE_FULLSTACK,
                "Test",
                BackendStack.JAVA_SPRING,
                null,
                null,
                null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isFalse();
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(contains("Frontend stack is required"));
    }

    @Test
    void isValid_shouldPass_whenFullstackPresetWithBothStacks() {
        when(presetAgentResolver.resolve(GenerationPreset.ENTERPRISE_FULLSTACK)).thenReturn(Set.of(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.FRONTEND_ARCHITECT
        ));

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.ENTERPRISE_FULLSTACK,
                "Test",
                BackendStack.JAVA_SPRING,
                FrontendStack.TYPESCRIPT_REACT,
                null,
                null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isTrue();
    }

    @Test
    void isValid_shouldPass_whenRequestIsNull() {
        boolean result = validator.isValid(null, context);

        assertThat(result).isTrue();
    }

    @Test
    void isValid_shouldPass_whenPresetIsNull() {
        GenerationRequest request = new GenerationRequest(
                null,
                "Test",
                null,
                null,
                null,
                null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isTrue();
    }
}
