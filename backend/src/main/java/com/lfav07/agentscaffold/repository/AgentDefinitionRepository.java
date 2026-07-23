package com.lfav07.agentscaffold.repository;

import com.lfav07.agentscaffold.model.definition.AgentDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentDefinitionRepository extends JpaRepository<AgentDefinition, Long> {
    Optional<AgentDefinition> findByAgentKey(String agentKey);
}
