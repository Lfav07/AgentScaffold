package com.lfav07.agentscaffold.dto.validation;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.exception.InvalidPresetException;
import com.lfav07.agentscaffold.model.agent.Agent;
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
        if (request == null || request.presetKey() == null) {
            return true;
        }

        Set<Agent> agents;
        try {
            agents = presetAgentResolver.resolve(request.presetKey());
        } catch (InvalidPresetException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode("presetKey").addConstraintViolation();
            return false;
        }

        boolean hasBackendAgents = agents.stream()
                .anyMatch(a -> a.getStack().getCategory() == StackCategory.BACKEND);
        boolean hasFrontendAgents = agents.stream()
                .anyMatch(a -> a.getStack().getCategory() == StackCategory.FRONTEND);

        if (hasBackendAgents && request.backendStack() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Backend stack is required for preset: " + request.presetKey()
            ).addPropertyNode("backendStack").addConstraintViolation();
            return false;
        }

        if (hasFrontendAgents && request.frontendStack() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Frontend stack is required for preset: " + request.presetKey()
            ).addPropertyNode("frontendStack").addConstraintViolation();
            return false;
        }

        return true;
    }
}
