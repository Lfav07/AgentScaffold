import {useFormContext} from "react-hook-form";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";

export default function ProjectNameStep(){
    const {register, formState} = useFormContext<GenerationRequestType>()
    return (
        <div className="flex flex-col items-center text-center gap-4">
            <h1 className="text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
                Step 1: Project name
            </h1>
            <p className="max-w-lg text-balance text-base leading-relaxed text-[var(--text)]">
                Insert the name of your project below. The project name shall be used to customize the agents generated.
            </p>
            <label className="flex flex-col items-start gap-2 w-full max-w-md">
                <span className="text-sm font-semibold text-[var(--text-h)]">
                    Project Name
                </span>
                <input
                    {...register("projectName")}
                    className="w-full rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 p-3 text-sm text-[var(--text)] outline-none transition-all duration-200 focus:border-emerald-500/50 focus:ring-2 focus:ring-emerald-500/20"
                />
            </label>
        </div>
    );
}