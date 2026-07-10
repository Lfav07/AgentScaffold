import Header from "../../../shared/components/Header/Header";
import {Button} from "@/components/ui/button.tsx";

export default function HomePage() {
    return <>
        <Header/>
        <main className={"bg-blue-500"}>
        </main>
        <Button>Generate</Button>
        <h1>Home page!</h1>
    </>
}