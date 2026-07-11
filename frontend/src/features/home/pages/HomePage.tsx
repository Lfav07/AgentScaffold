import Header from "../../../shared/components/Header/Header";
import Hero from "@/features/home/components/Hero";
import HowItWorks from "@/features/home/components/HowItWorks";
import ProductPreview from "@/features/home/components/ProductPreview";
import Features from "@/features/home/components/Features";
import FinalCTA from "@/features/home/components/FinalCTA";

export default function HomePage() {
  return (
    <>
      <Header />
      <main>
        <Hero />
        <ProductPreview />
        <HowItWorks />
        <Features />
        <FinalCTA />
      </main>
    </>
  );
}