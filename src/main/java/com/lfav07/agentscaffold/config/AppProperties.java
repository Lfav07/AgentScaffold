package com.lfav07.agentscaffold.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
    Paths paths,
    Generation generation,
    Stacks stacks
) {
    public record Paths(String definitions, String templates) {}

    public record Generation(
        String sanitizeRegex,
        String zipSuffix
    ) {}

    public record Stacks(
        CategoryLabels categoryLabels
    ) {
        public record CategoryLabels(String backend, String frontend) {}
    }
}
