package com.lfav07.agentscaffold.registry;

import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PresetRegistry {

    private static final Map<GenerationPreset, PresetItem> PRESETS = Map.ofEntries(
            Map.entry(GenerationPreset.ENTERPRISE_FULLSTACK,
                    new PresetItem("enterprise-fullstack", "Enterprise Fullstack", "Lorem ipsum")),
            Map.entry(GenerationPreset.ENTERPRISE_SPRING,
                    new PresetItem("enterprise-spring", "Enterprise Spring", "Lorem ipsum")),
            Map.entry(GenerationPreset.ENTERPRISE_REACT,
                    new PresetItem("enterprise-react", "Enterprise React", "Lorem ipsum")),
            Map.entry(GenerationPreset.STARTUP_READY,
                    new PresetItem("startup-ready", "Startup Ready", "Lorem ipsum")),
            Map.entry(GenerationPreset.REACT_READY,
                    new PresetItem("react-ready", "React Ready", "Lorem ipsum")),
            Map.entry(GenerationPreset.SPRING_READY,
                    new PresetItem("spring-ready", "Spring Ready", "Lorem ipsum"))
    );

    public static List<PresetItem> getPresets() {
        return new ArrayList<>(PRESETS.values());
    }

    public static GenerationPreset fromId(String id) {
        return GenerationPreset.fromValue(id);
    }
}
