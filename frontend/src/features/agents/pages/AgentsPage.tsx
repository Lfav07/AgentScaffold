import type {Agent} from "@/shared/types.ts";
import Header from "@/shared/components/Header/Header.tsx";
import {useAgents} from "@/features/agents/hooks/agentsHooks.ts";
import LoadingView from "@/features/generation/components/LoadingView.tsx";
import ErrorView from "@/features/generation/components/ErrorView.tsx";
import {useMemo} from "react";

type AgentCategory = "backend" | "frontend" | "general";

const categoryLabel: Record<AgentCategory, string> = {
    backend: "Backend",
    frontend: "Frontend",
    general: "General",
};

function getAgentCategory(agent: Agent): AgentCategory {
    if (agent.id.startsWith("backend")) return "backend";
    if (agent.id.startsWith("frontend")) return "frontend";
    return "general";
}

function AgentCard({agent}: {agent: Agent}) {
    return (
        <div className="group rounded-xl border border-[var(--border)] bg-[var(--bg)]/40 p-5 shadow-sm transition-all duration-200 hover:border-emerald-500/30 hover:shadow-md hover:shadow-[var(--accent)]/5">
            <h3 className="text-lg font-semibold text-[var(--text-h)]">
                {agent.name}
            </h3>
            <p className="mt-2 text-sm leading-relaxed text-[var(--text)]/80">
                {agent.description}
            </p>
        </div>
    );
}

function AgentSection({category, agents}: {category: AgentCategory; agents: Agent[]}) {
    if (agents.length === 0) return null;
    return (
        <section className="w-full max-w-5xl">
            <h2 className="text-2xl font-bold tracking-tight text-[var(--text-h)]">
                {categoryLabel[category]}
            </h2>
            <p className="mt-1 text-sm text-[var(--text)]/60">
                {agents.length} agent{agents.length !== 1 ? "s" : ""}
            </p>
            <div className="mt-4 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                {agents.map((agent) => (
                    <AgentCard key={agent.id} agent={agent} />
                ))}
            </div>
        </section>
    );
}

export default function AgentsPage() {
    const agentsList = useAgents();

    const grouped = useMemo(() => {
        if (!agentsList.data) return { backend: [], frontend: [], general: [] };

        const sorted = [...agentsList.data].sort((a, b) => a.name.localeCompare(b.name));
        const groups: Record<AgentCategory, Agent[]> = { backend: [], frontend: [], general: [] };
        for (const agent of sorted) {
            groups[getAgentCategory(agent)].push(agent);
        }
        return groups;
    }, [agentsList.data]);

    return (
        <div className="min-h-screen bg-gradient-to-r from-white to-[#abbaab] dark:from-[#0a0a0f] dark:to-[#1a2e2e]">
            <Header/>
            <main className="min-h-screen">
                {agentsList.isPending ? (
                    <LoadingView message={"Loading agents..."} />
                ) : agentsList.isError ? (
                    <ErrorView message={"Could not load agents. Please try again."} />
                ) : (
                    <section className="relative flex flex-col items-center px-4 py-20 sm:py-28">
                        <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
                        <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
                        <h1 className="text-4xl font-bold tracking-tight text-[var(--text-h)] sm:text-5xl">
                            Agents
                        </h1>
                        <p className="mt-3 max-w-lg text-balance text-center text-base leading-relaxed text-[var(--text)]">
                          All available agents direct from our catalog.
                        </p>
                        <div className="mt-12 flex w-full flex-col items-center gap-14">
                            <AgentSection category="backend" agents={grouped.backend} />
                            <AgentSection category="frontend" agents={grouped.frontend} />
                            <AgentSection category="general" agents={grouped.general} />
                        </div>
                    </section>
                )}
            </main>
        </div>
    );
}
