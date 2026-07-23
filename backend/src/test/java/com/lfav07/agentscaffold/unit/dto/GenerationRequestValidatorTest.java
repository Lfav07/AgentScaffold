package com.lfav07.agentscaffold.unit.dto;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.validation.GenerationRequestValidator;
import com.lfav07.agentscaffold.fixture.TestEntities;
import com.lfav07.agentscaffold.model.agent.Agent;
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
        when(presetAgentResolver.resolve("react-ready")).thenReturn(Set.of(
                TestEntities.frontendArchitect(),
                TestEntities.frontendImplementer(),
                TestEntities.frontendTester()
        ));

        GenerationRequest request = new GenerationRequest(
                "react-ready", "Test", null, "typescript-react", null, null
        );

        assertThat(validator.isValid(request, context)).isTrue();
    }

    @Test
    void isValid_shouldPass_whenBackendOnlyPresetWithFrontendStackNull() {
        when(presetAgentResolver.resolve("spring-ready")).thenReturn(Set.of(
                TestEntities.backendArchitect(),
                TestEntities.backendImplementer(),
                TestEntities.backendTester()
        ));

        GenerationRequest request = new GenerationRequest(
                "spring-ready", "Test", "java-spring", null, null, null
        );

        assertThat(validator.isValid(request, context)).isTrue();
    }

    @Test
    void isValid_shouldFail_whenFullstackPresetMissingBackendStack() {
        Set<Agent> agents = Set.of(
                TestEntities.backendArchitect(),
                TestEntities.frontendArchitect()
        );
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(agents);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(any())).thenReturn(nodeBuilder);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "Test", null, "typescript-react", null, null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isFalse();
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(contains("Backend stack is required"));
    }

    @Test
    void isValid_shouldFail_whenFullstackPresetMissingFrontendStack() {
        Set<Agent> agents = Set.of(
                TestEntities.backendArchitect(),
                TestEntities.frontendArchitect()
        );
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(agents);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(any())).thenReturn(nodeBuilder);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "Test", "java-spring", null, null, null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isFalse();
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(contains("Frontend stack is required"));
    }

    @Test
    void isValid_shouldPass_whenFullstackPresetWithBothStacks() {
        Set<Agent> agents = Set.of(
                TestEntities.backendArchitect(),
                TestEntities.frontendArchitect()
        );
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(agents);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "Test", "java-spring", "typescript-react", null, null
        );

        assertThat(validator.isValid(request, context)).isTrue();
    }

    @Test
    void isValid_shouldPass_whenRequestIsNull() {
        boolean result = validator.isValid(null, context);
        assertThat(result).isTrue();
    }

    @Test
    void isValid_shouldPass_whenPresetKeyIsNull() {
        GenerationRequest request = new GenerationRequest(
                null, "Test", null, null, null, null
        );

        boolean result = validator.isValid(request, context);

        assertThat(result).isTrue();
    }
}
