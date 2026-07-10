import Header from "../../../shared/components/Header/Header";
import Hero from "@/features/home/components/Hero";
import HowItWorks from "@/features/home/components/HowItWorks";

export default function HomePage() {
  return (
    <>
      <Header />
      <main>
        <Hero />
        <HowItWorks />
      </main>
    </>
  );
}