import {LoaderCircle} from "lucide-react";

type LoadingViewProps = {
    message: string;
};

export default function LoadingView({message}: LoadingViewProps) {
    return (
        <section className="relative flex flex-col items-center justify-center gap-6 px-4 py-24 sm:py-32">
            <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
            <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
            <LoaderCircle className="size-10 animate-spin text-emerald-400" />
            <p className="text-base text-[var(--text)]">{message}</p>
        </section>
    );
}
