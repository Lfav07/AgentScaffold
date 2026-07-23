package com.lfav07.agentscaffold.repository;

import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.model.preset.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PresetRepository extends JpaRepository<Long, Preset> {
    public Set<Agent> getAgentsByKey(String key);
}
