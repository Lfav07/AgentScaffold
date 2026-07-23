package com.lfav07.agentscaffold.model.stack;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stacks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String key;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private StackCategory category;
}
