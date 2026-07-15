package com.lfav07.agentscaffold.registry;

import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.model.agent.AdditionalAgentType;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AgentRegistry {

    private static final Map<CoreAgentType, AgentItem> CORE = Map.ofEntries(
            Map.entry(CoreAgentType.BACKEND_ARCHITECT,
                    new AgentItem("backend-architect", "Backend Architect", "Designs backend architecture, project structure, and technology decisions")),
            Map.entry(CoreAgentType.FRONTEND_ARCHITECT,
                    new AgentItem("frontend-architect", "Frontend Architect", "Designs frontend architecture, component tree, and UI/UX decisions")),
            Map.entry(CoreAgentType.BACKEND_ARCHITECT_REVIEWER,
                    new AgentItem("backend-architect-reviewer", "Backend Architect Reviewer", "Reviews backend architecture decisions and ensures best practices")),
            Map.entry(CoreAgentType.FRONTEND_ARCHITECT_REVIEWER,
                    new AgentItem("frontend-architect-reviewer", "Frontend Architect Reviewer", "Reviews frontend architecture decisions and ensures best practices")),
            Map.entry(CoreAgentType.BACKEND_IMPLEMENTER,
                    new AgentItem("backend-implementer", "Backend Implementer", "Implements backend code following the architecture blueprint")),
            Map.entry(CoreAgentType.FRONTEND_IMPLEMENTER,
                    new AgentItem("frontend-implementer", "Frontend Implementer", "Implements frontend components following the architecture blueprint")),
            Map.entry(CoreAgentType.BACKEND_TESTER,
                    new AgentItem("backend-tester", "Backend Tester", "Writes and executes backend tests to ensure reliability")),
            Map.entry(CoreAgentType.FRONTEND_TESTER,
                    new AgentItem("frontend-tester", "Frontend Tester", "Writes and executes frontend tests to ensure reliability")),
            Map.entry(CoreAgentType.FINAL_REVIEWER,
                    new AgentItem("final-reviewer", "Final Reviewer", "Performs cross-stack final review before feature delivery"))
    );

    //v2
    private static final Map<AdditionalAgentType, AgentItem> ADDITIONAL = Map.of(
            AdditionalAgentType.SQL_AGENT,
            new AgentItem("sql-agent", "SQL Agent", "Lorem ipsum")
    );

    public Set<AgentItem> getCoreAgents() {
        return new HashSet<>(CORE.values());
    }

    public Set<AgentItem> getAdditionalAgents() {
        return new HashSet<>(ADDITIONAL.values());
    }

    public CoreAgentType fromCoreAgentId(String id) {
        return CoreAgentType.fromValue(id);
    }

    public AdditionalAgentType fromAdditionalAgentId(String id) {
        return AdditionalAgentType.fromValue(id);
    }
}
