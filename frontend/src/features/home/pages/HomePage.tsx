import Header from "../../../shared/components/Header/Header";
import Hero from "@/features/home/components/Hero";
import HowItWorks from "@/features/home/components/HowItWorks";

import Features from "@/features/home/components/Features";
import FinalCTA from "@/features/home/components/FinalCTA";
import ProductPreview from "@/features/home/components/ProductPreview.tsx";

export default function HomePage() {
    return (
        <div className="min-h-screen bg-gradient-to-r from-white to-[#abbaab] dark:from-[#0a0a0f] dark:to-[#1a2e2e]">
            <Header/>
            <main>
                <Hero/>
                <ProductPreview/>
                <HowItWorks/>
                <Features/>
                <FinalCTA/>
            </main>
        </div>
    );
}