import ProgressIndicator from "@/features/generation/components/ProgressIndicator.tsx";
import {useRef, useState} from "react";
import {FormProvider, type SubmitHandler, useForm} from "react-hook-form";
import {generationRequestSchema, type GenerationRequestType, stepFields} from "@/features/generation/schemas.ts";
import {zodResolver} from "@hookform/resolvers/zod";
import ProjectNameStep from "@/features/generation/components/steps/ProjectNameStep.tsx";
import {useGeneration} from "@/features/generation/hooks/generationHooks.ts";
import {Button} from "@/components/ui/button";
import PresetStep from "@/features/generation/components/steps/PresetStep.tsx";
import {usePresets} from "@/features/presets/components/presetsHooks.ts";
import {useStacks} from "@/features/stacks/hooks/stacksHooks.ts";
import StackStep from "@/features/generation/components/steps/StackStep.tsx";
import PreviewStep from "@/features/generation/components/steps/PreviewStep.tsx";
import SuccessView from "@/features/generation/components/SuccessView.tsx";
import {LoaderCircle} from "lucide-react";

export default function MultiStepForm() {
    const [currentStep, setCurrentStep] = useState(0);
    const [submittedName, setSubmittedName] = useState("");
    const lastData = useRef<GenerationRequestType | null>(null);
    const generate = useGeneration();
    const form = useForm<GenerationRequestType>({
        resolver: zodResolver(generationRequestSchema),
        defaultValues: {
            projectName: "",
            preset: undefined,
            backendStack: undefined,
            frontendStack: undefined,
        }
    });
    const presets = usePresets()
    const stacks = useStacks()
    if (presets.isPending) {
        return <p>Loading...</p>;
    }

    if (presets.error) {
        return <p>Something went wrong.</p>;
    }
    if (stacks.isPending) {
        return <p>Loading...</p>;
    }

    if (stacks.error) {
        return <p>Something went wrong.</p>;
    }

    if (generate.isPending) {
        return (
            <section className="relative flex flex-col items-center justify-center gap-6 px-4 py-24 sm:py-32">
                <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
                <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
                <LoaderCircle className="size-10 animate-spin text-emerald-400" />
                <p className="text-base text-[var(--text)]">Generating your AI engineering team...</p>
            </section>
        );
    }

    if (generate.isSuccess && generate.data) {
        return (
            <SuccessView
                blob={generate.data}
                projectName={submittedName}
                onReset={() => { generate.reset(); form.reset(); setCurrentStep(0); setSubmittedName(""); }}
            />
        );
    }

    if (generate.error) {
        return (
            <section className="relative flex flex-col items-center justify-center gap-6 px-4 py-24 sm:py-32">
                <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
                <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
                <p className="text-base text-red-400">Something went wrong while generating your team.</p>
                <Button
                    size="lg"
                    className="h-10 rounded-xl border-0 bg-emerald-900 px-6 text-sm font-semibold text-white shadow-lg shadow-[var(--accent)]/25 transition-all duration-200 hover:bg-emerald-400/85 hover:shadow-[var(--accent)]/35 hover:scale-[1.02] active:scale-[0.98]"
                    onClick={() => { if (lastData.current) generate.mutate(lastData.current); }}
                >
                    Try again
                </Button>
            </section>
        );
    }

    const steps = [
        <ProjectNameStep key="project"/>,
        <PresetStep key={"preset"} presetsList={presets.data}/>,
        <StackStep  key={"stack"} backendStacks={stacks.data.backend} frontendStacks={stacks.data.frontend}/>,
        <PreviewStep key="preview" presetsList={presets.data} backendStacks={stacks.data.backend} frontendStacks={stacks.data.frontend}/>,
    ];

    const isLastStep = currentStep === steps.length - 1;

    async function handleNext() {
        const fieldsToValidate = stepFields[currentStep as keyof typeof stepFields];
        const isValid = await form.trigger(fieldsToValidate);
        if (isValid) setCurrentStep((s) => s + 1);
    }

    function handleBack() {
        setCurrentStep((s) => Math.max(0, s - 1));
    }

    const onSubmit: SubmitHandler<GenerationRequestType> = (data) => {
        setSubmittedName(data.projectName);
        lastData.current = data;
        generate.mutate(data);
    }

    const btnClass = "h-10 rounded-xl border-0 bg-emerald-900 px-6 text-sm font-semibold text-white shadow-lg shadow-[var(--accent)]/25 transition-all duration-200 hover:bg-emerald-400/85 hover:shadow-[var(--accent)]/35 hover:scale-[1.02] active:scale-[0.98]";

    return (
        <section className="mx-auto max-w-5xl px-4 py-20 sm:py-28">
            <div className="flex flex-col items-center gap-8">
                <ProgressIndicator step={currentStep} totalSteps={steps.length}/>
                <FormProvider {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)} className="flex flex-col items-center gap-6">
                        {steps[currentStep]}

                        <div className="flex gap-3 mt-4">
                            {currentStep > 0 && (
                                <Button type="button" variant="ghost" onClick={handleBack}>
                                    Back
                                </Button>
                            )}

                            {!isLastStep ? (
                                <Button type="button" size="lg" className={btnClass} onClick={handleNext}>
                                    Next
                                </Button>
                            ) : (
                                <Button type="button" size="lg" className={btnClass} onClick={() => form.handleSubmit(onSubmit)()}>
                                Submit
                                </Button>
                            )}
                        </div>
                    </form>
                </FormProvider>
            </div>
        </section>
    );
}