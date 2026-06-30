package com.lfav07.agentscaffold.model.preset;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidPresetException;


public enum GenerationPreset {
    ENTERPRISE_FULLSTACK,
    ENTERPRISE_SPRING,
    ENTERPRISE_REACT,
    STARTUP_READY,
    REACT_READY,
    SPRING_READY;

    /**
     * Resolves a case-insensitive string value to the corresponding generation preset constant.
     *
     * @param value the string value to resolve (may be null or malformed).
     * @return the matching GenerationPreset constant.
     * @throws InvalidPresetException if no match is found or the value is null.
     */
    @JsonCreator
    public static GenerationPreset fromValue(String value) {
        if (value == null) {
            throw new InvalidPresetException("Generation Preset cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return GenerationPreset.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidPresetException("Invalid Generation Preset: " + value);
        }
    }
}


