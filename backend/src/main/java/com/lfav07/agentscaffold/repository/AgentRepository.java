package com.lfav07.agentscaffold.repository;

import com.lfav07.agentscaffold.model.agent.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Long, Agent> {
}
