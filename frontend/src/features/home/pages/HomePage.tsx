import Header from "../../../shared/components/Header/Header";
import Hero from "@/features/home/components/Hero.tsx";
import HowItWorks from "@/features/home/components/HowItWorks.tsx";

export default function HomePage() {
    return <>
        <Header/>
        <main className={"bg-blue-500"}>
            <Hero></Hero>
           <HowItWorks></HowItWorks>
        </main>
    </>
}