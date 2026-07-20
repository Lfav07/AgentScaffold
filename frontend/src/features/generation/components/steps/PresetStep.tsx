import {useFormContext} from "react-hook-form";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";
import type {Preset} from "@/shared/types.ts";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";

type PresetStepProps = {
    presetsList: Preset[];
};
export default function PresetStep({ presetsList }: PresetStepProps) {
    const {setValue, watch} = useFormContext<GenerationRequestType>()
    const selectedPreset = watch("preset")
    const sortedPresets = [...presetsList].sort((a, b) => a.name.localeCompare(b.name))
    const presetName = sortedPresets.find(p => p.id === selectedPreset)?.name
    return (
        <div className="flex flex-col items-center text-center gap-4">
            <h1 className="text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
                Step 2: Preset
            </h1>
            <p className="max-w-lg text-balance text-base leading-relaxed text-[var(--text)]">
                Pick a preset from our curated presets list. The preset shall be used to determine which agents will
                be delivered.
            </p>
            <label className="flex flex-col items-start gap-2 w-full max-w-md">
                <span className="text-sm font-semibold text-[var(--text-h)]">
                    Preset
                </span>
                <Select 
                    value={selectedPreset ?? ""}
                    onValueChange={(val) => { if (val) setValue("preset", val as GenerationRequestType["preset"]); }}
                >
                    <SelectTrigger className="w-full rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 px-3 py-3 text-sm text-[var(--text)] shadow-none transition-all duration-200 focus-visible:border-emerald-500/50 focus-visible:ring-2 focus-visible:ring-emerald-500/20 data-placeholder:text-[var(--text)]/40">
                        <SelectValue placeholder="Select a preset...">
                            {presetName}
                        </SelectValue>
                    </SelectTrigger>
                    <SelectContent>
                        {sortedPresets.map((preset) => (
                            <SelectItem key={preset.id} value={preset.id}>
                                {preset.name}
                            </SelectItem>
                        ))}
                    </SelectContent>
                </Select>
            </label>
        </div>
    );
}
