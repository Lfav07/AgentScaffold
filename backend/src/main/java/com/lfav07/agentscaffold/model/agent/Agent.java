package com.lfav07.agentscaffold.model.agent;

import com.lfav07.agentscaffold.model.stack.Stack;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String key;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column
    @Enumerated(EnumType.STRING)
    private CoreAgentType type;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

}
