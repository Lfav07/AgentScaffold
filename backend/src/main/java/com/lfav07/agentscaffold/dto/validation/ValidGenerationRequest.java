package com.lfav07.agentscaffold.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenerationRequestValidator.class)
public @interface ValidGenerationRequest {
    String message() default "Backend or frontend stack is required for the selected preset";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
