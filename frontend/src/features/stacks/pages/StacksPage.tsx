import type {Stack} from "@/shared/types.ts";
import Header from "@/shared/components/Header/Header.tsx";
import {useStacks} from "@/features/stacks/hooks/stacksHooks.ts";
import LoadingView from "@/features/generation/components/LoadingView.tsx";
import ErrorView from "@/features/generation/components/ErrorView.tsx";
import {useMemo} from "react";

type StackCategory = "backend" | "frontend";

const categoryLabel: Record<StackCategory, string> = {
    backend: "Backend",
    frontend: "Frontend",
};

const categoryIcon: Record<StackCategory, string> = {
    backend: "\u2699",
    frontend: "\u25C7",
};

function StackCard({stack, category}: {stack: Stack; category: StackCategory}) {
    return (
        <div className="group rounded-xl border border-[var(--border)] bg-[var(--bg)]/40 p-5 shadow-sm transition-all duration-200 hover:border-emerald-500/30 hover:shadow-md hover:shadow-[var(--accent)]/5">
            <div className="flex items-center gap-3">
                <span className="flex size-9 items-center justify-center rounded-lg bg-[var(--accent)]/10 text-base">
                    {categoryIcon[category]}
                </span>
                <h3 className="text-lg font-semibold text-[var(--text-h)]">
                    {stack.name}
                </h3>
            </div>
        </div>
    );
}

function StackSection({category, stacks}: {category: StackCategory; stacks: Stack[]}) {
    if (stacks.length === 0) return null;
    const sorted = [...stacks].sort((a, b) => a.name.localeCompare(b.name));
    return (
        <section className="w-full max-w-5xl">
            <h2 className="text-2xl font-bold tracking-tight text-[var(--text-h)]">
                {categoryLabel[category]}
            </h2>
            <p className="mt-1 text-sm text-[var(--text)]/60">
                {stacks.length} stack{stacks.length !== 1 ? "s" : ""}
            </p>
            <div className="mt-4 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                {sorted.map((stack) => (
                    <StackCard key={stack.id} stack={stack} category={category} />
                ))}
            </div>
        </section>
    );
}

export default function StacksPage() {
    const stacksList = useStacks()
    const backendStacks = useMemo(
        () => stacksList.data?.backend ?? [],
        [stacksList.data]
    );
    const frontendStacks = useMemo(
        () => stacksList.data?.frontend ?? [],
        [stacksList.data]
    );

    return (
        <div className="app-page">
            <Header/>
            <main className="min-h-screen">
                {stacksList.isPending ? (
                    <LoadingView message={"Loading stacks..."} />
                ) : stacksList.error ? (
                    <ErrorView message={"Could not load stacks. Please try again."} />
                ) : (
                    <section className="relative flex flex-col items-center overflow-hidden px-4 py-20 sm:py-28">
                        <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
                        <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
                        <h1 className="text-4xl font-bold tracking-tight text-[var(--text-h)] sm:text-5xl">
                            Stacks
                        </h1>
                        <p className="mt-3 max-w-lg text-balance text-center text-base leading-relaxed text-[var(--text)]">
                            Technology stacks available for your generated projects.
                        </p>
                        <div className="mt-12 flex w-full flex-col items-center gap-14">
                            <StackSection category="backend" stacks={backendStacks} />
                            <StackSection category="frontend" stacks={frontendStacks} />
                        </div>
                    </section>
                )}
            </main>
        </div>
    );
}
