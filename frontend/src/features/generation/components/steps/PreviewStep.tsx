import {useFormContext} from "react-hook-form";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";
import type {Preset, Stack} from "@/shared/types.ts";
import {presetStackRequirements} from "@/features/generation/presetStacks.ts";

type PreviewStepProps = {
    presetsList: Preset[];
    backendStacks: Stack[];
    frontendStacks: Stack[];
};
export default function PreviewStep({presetsList, backendStacks, frontendStacks}: PreviewStepProps) {
    const {watch} = useFormContext<GenerationRequestType>()
    const projectName = watch("projectName")
    const selectedPreset = watch("presetKey")
    const selectedBackend = watch("backendStack")
    const selectedFrontend = watch("frontendStack")

    const presetName = presetsList.find(p => p.id === selectedPreset)?.name
    const backendName = backendStacks.find(s => s.id === selectedBackend)?.name
    const frontendName = frontendStacks.find(s => s.id === selectedFrontend)?.name
    const reqs = selectedPreset ? presetStackRequirements[selectedPreset] : undefined

    return (
        <div className="flex flex-col items-center text-center gap-4">
            <h1 className="text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
                Step 4: Review
            </h1>
            <p className="max-w-lg text-balance text-base leading-relaxed text-[var(--text)]">
                Review your configuration before generating your AI engineering team.
            </p>
            <div className="w-full max-w-lg rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 p-6 text-left">
                <div className="space-y-5">
                    <div>
                        <span className="text-sm font-semibold text-[var(--text-h)]">Project Name</span>
                        <p className="mt-1 text-base text-[var(--text)]">{projectName || "\u2014"}</p>
                    </div>
                    <div>
                        <span className="text-sm font-semibold text-[var(--text-h)]">Preset</span>
                        <p className="mt-1 text-base text-[var(--text)]">{presetName || "\u2014"}</p>
                    </div>
                    <div>
                        <span className="text-sm font-semibold text-[var(--text-h)]">Backend Stack</span>
                        <p className="mt-1 text-base text-[var(--text)]">
                            {reqs?.backend ? (backendName || "\u2014") : <span className="text-[var(--text)]/40">Not required</span>}
                        </p>
                    </div>
                    <div>
                        <span className="text-sm font-semibold text-[var(--text-h)]">Frontend Stack</span>
                        <p className="mt-1 text-base text-[var(--text)]">
                            {reqs?.frontend ? (frontendName || "\u2014") : <span className="text-[var(--text)]/40">Not required</span>}
                        </p>
                    </div>
                </div>
            </div>
            <p className="text-xs text-[var(--text)]/40">
                Use the Back button to make changes.
            </p>
        </div>
    );
}
