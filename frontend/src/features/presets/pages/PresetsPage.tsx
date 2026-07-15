import Header from "@/shared/components/Header/Header.tsx";
import {usePresets} from "@/features/presets/hooks/presetsHooks.ts";
import LoadingView from "@/features/generation/components/LoadingView.tsx";
import ErrorView from "@/features/generation/components/ErrorView.tsx";
import {useMemo} from "react";

export default function PresetsPage() {
    const presetsList = usePresets()
    if (presetsList.isPending) return <LoadingView message={"Loading presets..."} />;
    if (presetsList.error) return <ErrorView message={"Could not load presets. Please try again."} />;

    const sorted = useMemo(
        () => [...presetsList.data].sort((a, b) => a.name.localeCompare(b.name)),
        [presetsList.data]
    );

    return (
        <div className="bg-gradient-to-r from-white to-[#abbaab]">
            <Header/>
            <main className="min-h-screen">
                <section className="relative flex flex-col items-center px-4 py-20 sm:py-28">
                    <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
                    <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
                    <h1 className="text-4xl font-bold tracking-tight text-[var(--text-h)] sm:text-5xl">
                        Presets
                    </h1>
                    <p className="mt-3 max-w-lg text-balance text-center text-base leading-relaxed text-[var(--text)]">
                        Curated configurations that define which agents and stacks are included in your project.
                    </p>
                    <div className="mt-12 grid w-full max-w-5xl gap-4 sm:grid-cols-2 lg:grid-cols-3">
                        {sorted.map((preset) => (
                            <div key={preset.id} className="group rounded-xl border border-[var(--border)] bg-[var(--bg)]/40 p-5 shadow-sm transition-all duration-200 hover:border-emerald-500/30 hover:shadow-md hover:shadow-[var(--accent)]/5">
                                <h3 className="text-lg font-semibold text-[var(--text-h)]">
                                    {preset.name}
                                </h3>
                                <p className="mt-2 text-sm leading-relaxed text-[var(--text)]/80">
                                    {preset.description}
                                </p>
                            </div>
                        ))}
                    </div>
                </section>
            </main>
        </div>
    );
}