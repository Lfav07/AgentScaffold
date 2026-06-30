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
            Map.entry(GenerationPreset.ENTERPRISE_SPRING,
                    new PresetItem("enterprise-spring", "Enterprise Spring", "Complete backend pipeline for Spring projects with full review and testing")),
            Map.entry(GenerationPreset.ENTERPRISE_REACT,
                    new PresetItem("enterprise-react", "Enterprise React", "Complete frontend pipeline for React projects with full review and testing")),
            Map.entry(GenerationPreset.STARTUP_READY,
                    new PresetItem("startup-ready", "Startup Ready", "Fast-paced fullstack setup for startups, covering both frontend and backend essentials")),
            Map.entry(GenerationPreset.REACT_READY,
                    new PresetItem("react-ready", "React Ready", "Streamlined frontend setup for rapid React development")),
            Map.entry(GenerationPreset.SPRING_READY,
                    new PresetItem("spring-ready", "Spring Ready", "Streamlined backend setup for rapid Spring development"))
    );

    public  Set<PresetItem> getPresets() {
        return new HashSet<>(PRESETS.values());
    }

    public static GenerationPreset fromId(String id) {
        return GenerationPreset.fromValue(id);
    }
}
