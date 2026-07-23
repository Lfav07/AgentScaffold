package com.lfav07.agentscaffold.repository;

import com.lfav07.agentscaffold.model.agent.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByKey(String key);
    List<Agent> findByCategory(String category);
}
