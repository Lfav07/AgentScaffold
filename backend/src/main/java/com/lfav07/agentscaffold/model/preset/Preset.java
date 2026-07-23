package com.lfav07.agentscaffold.model.preset;

import com.lfav07.agentscaffold.model.agent.Agent;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "presets")
public class Preset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String key;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany
    @JoinTable(
        name = "preset_agents",
        joinColumns = @JoinColumn(name = "preset_id"),
        inverseJoinColumns = @JoinColumn(name = "agent_id")
    )
    private Set<Agent> agents;
}
