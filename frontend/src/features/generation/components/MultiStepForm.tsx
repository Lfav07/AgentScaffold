import ProgressIndicator from "@/features/generation/components/ProgressIndicator.tsx";
import {useState} from "react";
import {FormProvider, type SubmitHandler, useForm} from "react-hook-form";
import {generationRequestSchema, type GenerationRequestType, stepFields} from "@/features/generation/schemas.ts";
import {zodResolver} from "@hookform/resolvers/zod";
import ProjectNameStep from "@/features/generation/components/steps/ProjectNameStep.tsx";
import {useGeneration} from "@/features/generation/hooks/generationHooks.ts";
import {Button} from "@/components/ui/button";
import PresetStep from "@/features/generation/components/steps/PresetStep.tsx";
import {usePresets} from "@/features/presets/components/presetsHooks.ts";

export default function MultiStepForm() {
    const [currentStep, setCurrentStep] = useState(0);
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
    if (presets.isPending) {
        return <p>Loading...</p>;
    }

    if (presets.error) {
        return <p>Something went wrong.</p>;
    }
    const steps = [
        <ProjectNameStep key="project"/>,
        <PresetStep key={"preset"} presetsList={presets.data}/>
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

    const onSubmit: SubmitHandler<GenerationRequestType> = (data) => generate.mutate(data)

    const btnClass = "h-10 rounded-xl border-0 bg-emerald-900 px-6 text-sm font-semibold text-white shadow-lg shadow-[var(--accent)]/25 transition-all duration-200 hover:bg-emerald-400/85 hover:shadow-[var(--accent)]/35 hover:scale-[1.02] active:scale-[0.98]";

    return (
        <section className="mx-auto max-w-5xl px-4 py-20 sm:py-28">
            <div className="flex flex-col items-center gap-8">
                <ProgressIndicator step={currentStep}/>
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
                                <Button type="submit" size="lg" className={btnClass}>
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