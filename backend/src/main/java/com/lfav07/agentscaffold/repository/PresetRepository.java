package com.lfav07.agentscaffold.repository;

import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.model.preset.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PresetRepository extends JpaRepository<Preset, Long> {
    Set<Agent> getAgentsByKey(String key);
    Optional<Preset> findByKey(String key);
    @Query("SELECT p FROM Preset p LEFT JOIN FETCH p.agents WHERE p.key = :key")
    Optional<Preset> findByKeyWithAgents(@Param("key") String key);
}
