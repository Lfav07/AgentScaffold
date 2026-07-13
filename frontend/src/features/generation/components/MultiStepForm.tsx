import ProgressIndicator from "@/features/generation/components/ProgressIndicator.tsx";
import {useState} from "react";
import {FormProvider, type SubmitHandler, useForm} from "react-hook-form";
import {generationRequestSchema, type GenerationRequestType, stepFields} from "@/features/generation/schemas.ts";
import {zodResolver} from "@hookform/resolvers/zod";
import ProjectNameStep from "@/features/generation/components/steps/ProjectNameStep.tsx";
import {useGeneration} from "@/features/generation/hooks/generationHooks.ts";

export default function MultiStepForm(){
    const [currentStep, setCurrentStep] = useState(0);
    const nextStep = () => setCurrentStep((step) => step + 1)
    const previousStep = () => setCurrentStep((step) => step - 1)
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
    const steps = [
        <ProjectNameStep key="project" />
    ]
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

    return (
        // temp background
        <div className={"w-screen mx-auto h-200 bg-red-200 flex m-10 flex-col items-center align-center"}>
            <ProgressIndicator step={currentStep}></ProgressIndicator>
            <FormProvider {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)}>
                    {steps[currentStep]}

                    <div style={{ display: "flex", gap: 8, marginTop: 16 }}>
                        {currentStep > 0 && (
                            <button type="button" onClick={handleBack}>
                                Back
                            </button>
                        )}

                        {!isLastStep ? (
                            <button type="button" onClick={handleNext}>
                                Next
                            </button>
                        ) : (
                            <button type="submit">Submit</button>
                        )}
                    </div>
                  </form>
            </FormProvider>
        </div>
    );
}