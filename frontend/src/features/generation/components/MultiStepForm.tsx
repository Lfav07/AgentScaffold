import ProgressIndicator from "@/features/generation/components/ProgressIndicator.tsx";
import {useState} from "react";
import {useForm} from "react-hook-form";
import {generationRequestSchema, type GenerationRequestType} from "@/features/generation/schemas.ts";
import {zodResolver} from "@hookform/resolvers/zod";

export default function MultiStepForm(){
    const [currentStep, setCurrentStep] = useState(0);


    const form = useForm<GenerationRequestType>({
        resolver: zodResolver(generationRequestSchema),
        defaultValues: {
            projectName: "",
            preset: undefined,
            backendStack: undefined,
            frontendStack: undefined,
        }
    });

    return (
        <div className={"w-screen mx-auto h-200 bg-red-200 flex m-10 flex-col items-center align-center"}>
            <ProgressIndicator step={currentStep}></ProgressIndicator>
            <h2>Future multi step form here!</h2>
        </div>
    );
}