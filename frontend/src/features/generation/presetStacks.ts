type PresetKey = "enterprise-fullstack" | "enterprise-backend" | "enterprise-frontend" | "startup-ready" | "backend-ready" | "frontend-ready";

export const presetStackRequirements: Record<PresetKey, { backend: boolean; frontend: boolean }> = {
    "enterprise-fullstack": { backend: true, frontend: true },
    "enterprise-backend":    { backend: true, frontend: false },
    "enterprise-frontend":     { backend: false, frontend: true },
    "startup-ready":        { backend: true, frontend: true },
    "backend-ready":         { backend: true, frontend: false },
    "frontend-ready":          { backend: false, frontend: true },
};

export const stackRequirementMessages: Record<PresetKey, { backend?: string; frontend?: string }> = {
    "enterprise-fullstack": { backend: "This preset requires a backend stack.", frontend: "This preset requires a frontend stack." },
    "enterprise-backend":    { backend: "This preset requires a backend stack." },
    "enterprise-frontend":     { frontend: "This preset requires a frontend stack." },
    "startup-ready":        { backend: "This preset requires a backend stack.", frontend: "This preset requires a frontend stack." },
    "backend-ready":         { backend: "This preset requires a backend stack." },
    "frontend-ready":          { frontend: "This preset requires a frontend stack." },
};
