import Header from "@/shared/components/Header/Header.tsx";
import ProgressIndicator from "@/features/generation/components/ProgressIndicator.tsx";
import {useState} from "react";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {generationRequestSchema, type GenerationRequestType} from "@/features/generation/schemas.ts";
export default function GenerationPage() {
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
        <div className={"bg-gradient-to-r from-white to-[#abbaab]"}>
            <Header/>
            <main className={"h-screen"}>
                <div className={"w-screen mx-auto h-200 bg-red-200 flex m-10 flex-col items-center align-center"}>
                    <ProgressIndicator step={currentStep}></ProgressIndicator>
                    <h2>Future multi step form here!</h2>
                </div>
            </main>
        </div>
    );
}