package com.lfav07.agentscaffold.registry;

import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PresetRegistry {

    private static final Map<GenerationPreset, PresetItem> PRESETS = Map.ofEntries(
            Map.entry(GenerationPreset.ENTERPRISE_FULLSTACK,
                    new PresetItem("enterprise-fullstack", "Enterprise Fullstack", "Full pipeline, supporting most complex developments")),
            Map.entry(GenerationPreset.ENTERPRISE_BACKEND,
                    new PresetItem("enterprise-backend", "Enterprise Backend", "Complete backend pipeline for projects with full review and testing")),
            Map.entry(GenerationPreset.ENTERPRISE_FRONTEND,
                    new PresetItem("enterprise-frontend", "Enterprise Frontend", "Complete frontend pipeline for projects with full review and testing")),
            Map.entry(GenerationPreset.STARTUP_READY,
                    new PresetItem("startup-ready", "Startup Ready", "Fast-paced fullstack setup for startups, covering both frontend and backend essentials")),
            Map.entry(GenerationPreset.FRONTEND_READY,
                    new PresetItem("frontend-ready", "Frontend Ready", "Streamlined frontend setup for rapid development")),
            Map.entry(GenerationPreset.BACKEND_READY,
                    new PresetItem("backend-ready", "Backend Ready", "Streamlined backend setup for rapid development"))
    );

    public  Set<PresetItem> getPresets() {
        return new HashSet<>(PRESETS.values());
    }

    public static GenerationPreset fromId(String id) {
        return GenerationPreset.fromValue(id);
    }
}
