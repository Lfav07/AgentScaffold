import Header from "../../../shared/components/Header/Header";
import Hero from "@/features/home/components/Hero";
import HowItWorks from "@/features/home/components/HowItWorks";

import Features from "@/features/home/components/Features";
import FinalCTA from "@/features/home/components/FinalCTA";

export default function HomePage() {
  return (
   <div className={"bg-gradient-to-r from-white to-[#abbaab]"}>
      <Header />
      <main >
        <Hero />
        <HowItWorks />
        <Features />
        <FinalCTA />
      </main>
   </div>
  );
}