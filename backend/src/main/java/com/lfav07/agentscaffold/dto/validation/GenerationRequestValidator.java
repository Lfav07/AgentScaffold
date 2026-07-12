package com.lfav07.agentscaffold.dto.validation;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.StackCategory;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class GenerationRequestValidator
        implements ConstraintValidator<ValidGenerationRequest, GenerationRequest> {

    private final PresetAgentResolver presetAgentResolver;

    @Override
    public boolean isValid(GenerationRequest request, ConstraintValidatorContext context) {
        if (request == null || request.preset() == null) {
            return true;
        }

        Set<CoreAgentType> agents = presetAgentResolver.resolve(request.preset());

        boolean hasBackendAgents = agents.stream()
                .anyMatch(a -> a.getStackCategory() == StackCategory.BACKEND);
        boolean hasFrontendAgents = agents.stream()
                .anyMatch(a -> a.getStackCategory() == StackCategory.FRONTEND);

        if (hasBackendAgents && request.backendStack() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Backend stack is required for preset: " + request.preset()
            ).addPropertyNode("backendStack").addConstraintViolation();
            return false;
        }

        if (hasFrontendAgents && request.frontendStack() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Frontend stack is required for preset: " + request.preset()
            ).addPropertyNode("frontendStack").addConstraintViolation();
            return false;
        }

        return true;
    }
}
