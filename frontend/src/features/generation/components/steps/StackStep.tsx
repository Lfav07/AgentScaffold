import {useFormContext} from "react-hook-form";
import * as z from "zod";
import type {GenerationRequestType, BackendStackEnum, FrontendStackEnum} from "@/features/generation/schemas.ts";
import type {Stack} from "@/shared/types.ts";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";

type StackStepProps = {
    backendStacks: Stack[];
    frontendStacks: Stack[];
};
export default function StackStep({backendStacks, frontendStacks}: StackStepProps){
    const {setValue, watch} = useFormContext<GenerationRequestType>()
    const selectedBackend = watch("backendStack")
    const selectedFrontend = watch("frontendStack")
    const sortedBackend = [...backendStacks].sort((a, b) => a.name.localeCompare(b.name))
    const sortedFrontend = [...frontendStacks].sort((a, b) => a.name.localeCompare(b.name))
    const backendName = sortedBackend.find(s => s.id === selectedBackend)?.name
    const frontendName = sortedFrontend.find(s => s.id === selectedFrontend)?.name

    return (
        <div className="flex flex-col items-center text-center gap-4">
            <h1 className="text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
                Step 3: Stacks
            </h1>
            <p className="max-w-lg text-balance text-base leading-relaxed text-[var(--text)]">
                Choose your backend and frontend stacks. Every generated agent will be customized for your chosen technologies.
            </p>
            <div className="grid w-full max-w-2xl gap-6 sm:grid-cols-2">
                <label className="flex flex-col items-start gap-2">
                    <span className="text-sm font-semibold text-[var(--text-h)]">
                        Backend
                    </span>
                    <Select
                        value={selectedBackend ?? ""}
                        onValueChange={(val) => { if (val) setValue("backendStack", val as z.infer<typeof BackendStackEnum>); }}
                    >
                        <SelectTrigger className="w-full rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 px-3 py-3 text-sm text-[var(--text)] shadow-none transition-all duration-200 focus-visible:border-emerald-500/50 focus-visible:ring-2 focus-visible:ring-emerald-500/20 data-placeholder:text-[var(--text)]/40">
                            <SelectValue placeholder="Select a backend...">
                                {backendName}
                            </SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {sortedBackend.map((stack) => (
                                <SelectItem key={stack.id} value={stack.id}>
                                    {stack.name}
                                </SelectItem>
                            ))}
                        </SelectContent>
                    </Select>
                </label>
                <label className="flex flex-col items-start gap-2">
                    <span className="text-sm font-semibold text-[var(--text-h)]">
                        Frontend
                    </span>
                    <Select
                        value={selectedFrontend ?? ""}
                        onValueChange={(val) => { if (val) setValue("frontendStack", val as z.infer<typeof FrontendStackEnum>); }}
                    >
                        <SelectTrigger className="w-full rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 px-3 py-3 text-sm text-[var(--text)] shadow-none transition-all duration-200 focus-visible:border-emerald-500/50 focus-visible:ring-2 focus-visible:ring-emerald-500/20 data-placeholder:text-[var(--text)]/40">
                            <SelectValue placeholder="Select a frontend...">
                                {frontendName}
                            </SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {sortedFrontend.map((stack) => (
                                <SelectItem key={stack.id} value={stack.id}>
                                    {stack.name}
                                </SelectItem>
                            ))}
                        </SelectContent>
                    </Select>
                </label>
            </div>
        </div>
    );
}
