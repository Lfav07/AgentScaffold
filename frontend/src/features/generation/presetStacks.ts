type PresetKey = "enterprise-fullstack" | "enterprise-spring" | "enterprise-react" | "startup-ready" | "spring-ready" | "react-ready";

export const presetStackRequirements: Record<PresetKey, { backend: boolean; frontend: boolean }> = {
    "enterprise-fullstack": { backend: true, frontend: true },
    "enterprise-spring":    { backend: true, frontend: false },
    "enterprise-react":     { backend: false, frontend: true },
    "startup-ready":        { backend: true, frontend: true },
    "spring-ready":         { backend: true, frontend: false },
    "react-ready":          { backend: false, frontend: true },
};

export const stackRequirementMessages: Record<PresetKey, { backend?: string; frontend?: string }> = {
    "enterprise-fullstack": { backend: "This preset requires a backend stack.", frontend: "This preset requires a frontend stack." },
    "enterprise-spring":    { backend: "This preset requires a backend stack." },
    "enterprise-react":     { frontend: "This preset requires a frontend stack." },
    "startup-ready":        { backend: "This preset requires a backend stack.", frontend: "This preset requires a frontend stack." },
    "spring-ready":         { backend: "This preset requires a backend stack." },
    "react-ready":          { frontend: "This preset requires a frontend stack." },
};
