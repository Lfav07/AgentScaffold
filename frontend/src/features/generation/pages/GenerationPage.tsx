import Header from "@/shared/components/Header/Header.tsx";
import MultiStepForm from "@/features/generation/components/MultiStepForm.tsx";
export default function GenerationPage() {

    return (
        <div className="min-h-screen overflow-x-hidden bg-gradient-to-r from-white to-[#abbaab] dark:from-[#0a0a0f] dark:to-[#1a2e2e]">
            <Header/>
            <main className={"min-h-screen"}>
            <MultiStepForm/>
            </main>
        </div>
    );
}