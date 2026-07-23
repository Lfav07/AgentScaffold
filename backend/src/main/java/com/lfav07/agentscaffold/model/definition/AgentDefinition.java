package com.lfav07.agentscaffold.model.definition;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "agent_definitions")
public class AgentDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_key", unique = true, nullable = false)
    private String agentKey;

    @Column(length = 65535)
    private String content;
}
