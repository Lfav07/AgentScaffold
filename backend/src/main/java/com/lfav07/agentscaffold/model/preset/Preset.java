package com.lfav07.agentscaffold.model.preset;

import com.lfav07.agentscaffold.model.agent.Agent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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

    @OneToMany
    @Column
    private Set<Agent> agents;
}
