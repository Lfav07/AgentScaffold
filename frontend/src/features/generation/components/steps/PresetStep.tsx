import {useFormContext} from "react-hook-form";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";
import type {Preset} from "@/shared/types.ts";

type PresetStepProps = {
    presetsList: Preset[];
};
export default function PresetStep({ presetsList }: PresetStepProps) {
    const {register} = useFormContext<GenerationRequestType>()
    const listItems = presetsList.map(preset => <option key={preset.id} value={preset.id}>{preset.name}</option>)
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
                <select
                    {...register("preset")}
                    className="w-full rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 p-3 text-sm text-[var(--text)] outline-none transition-all duration-200 focus:border-emerald-500/50 focus:ring-2 focus:ring-emerald-500/20"
                >
                    <option value="" disabled>Select a preset...</option>
                    {listItems}
                </select>
            </label>
        </div>
    );
}