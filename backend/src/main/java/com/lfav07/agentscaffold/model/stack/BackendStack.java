package com.lfav07.agentscaffold.model.stack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lfav07.agentscaffold.exception.InvalidStackException;


public enum BackendStack implements  Stack {
    JAVA_SPRING("java-spring"),
    TYPESCRIPT_NODE("typescript-node"),
    PYTHON_FASTAPI("python-fastapi");

    @JsonValue
    private final String id;

    BackendStack(String id){
        this.id = id;
    }

    @Override
    public String getId(){return  id;}

    /**
     * Resolves a case-insensitive string value to the corresponding backend stack constant.
     *
     * @param value the string value to resolve (may be null or malformed).
     * @return the matching BackendStack constant.
     * @throws InvalidStackException if no match is found or the value is null.
     */
    @JsonCreator
    public static BackendStack fromValue(String value) {
        if (value == null) {
            throw new InvalidStackException("Backend Stack cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return BackendStack.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStackException("Invalid Backend Stack: " + value);
        }
    }
}
