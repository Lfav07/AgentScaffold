import Header from "@/shared/components/Header/Header.tsx";
import MultiStepForm from "@/features/generation/components/MultiStepForm.tsx";
export default function GenerationPage() {
    return (
        <div className={"bg-gradient-to-r from-white to-[#abbaab]"}>
            <Header/>
            <main className={"h-screen"}>
                <MultiStepForm />
            </main>
        </div>
    );
}