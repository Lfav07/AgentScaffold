package com.lfav07.agentscaffold.registry;

import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.model.agent.AdditionalAgentType;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgentRegistry {

    private static final Map<CoreAgentType, AgentItem> CORE = Map.ofEntries(
            Map.entry(CoreAgentType.BACKEND_ARCHITECT,
                    new AgentItem("backend-architect", "Backend Architect", "Lorem ipsum")),
            Map.entry(CoreAgentType.FRONTEND_ARCHITECT,
                    new AgentItem("frontend-architect", "Frontend Architect", "Lorem ipsum")),
            Map.entry(CoreAgentType.BACKEND_ARCHITECT_REVIEWER,
                    new AgentItem("backend-architect-reviewer", "Backend Architect Reviewer", "Lorem ipsum")),
            Map.entry(CoreAgentType.FRONTEND_ARCHITECT_REVIEWER,
                    new AgentItem("frontend-architect-reviewer", "Frontend Architect Reviewer", "Lorem ipsum")),
            Map.entry(CoreAgentType.BACKEND_IMPLEMENTER,
                    new AgentItem("backend-implementer", "Backend Implementer", "Lorem ipsum")),
            Map.entry(CoreAgentType.FRONTEND_IMPLEMENTER,
                    new AgentItem("frontend-implementer", "Frontend Implementer", "Lorem ipsum")),
            Map.entry(CoreAgentType.BACKEND_TESTER,
                    new AgentItem("backend-tester", "Backend Tester", "Lorem ipsum")),
            Map.entry(CoreAgentType.FRONTEND_TESTER,
                    new AgentItem("frontend-tester", "Frontend Tester", "Lorem ipsum")),
            Map.entry(CoreAgentType.FINAL_REVIEWER,
                    new AgentItem("final-reviewer", "Final Reviewer", "Lorem ipsum"))
    );

    private static final Map<AdditionalAgentType, AgentItem> ADDITIONAL = Map.of(
            AdditionalAgentType.SQL_AGENT,
            new AgentItem("sql-agent", "SQL Agent", "Lorem ipsum")
    );

    public static List<AgentItem> getCoreAgents() {
        return new ArrayList<>(CORE.values());
    }

    public static List<AgentItem> getAdditionalAgents() {
        return new ArrayList<>(ADDITIONAL.values());
    }

    public static CoreAgentType fromCoreAgentId(String id) {
        return CoreAgentType.fromValue(id);
    }

    public static AdditionalAgentType fromAdditionalAgentId(String id) {
        return AdditionalAgentType.fromValue(id);
    }
}
