import {useCallback} from "react";
import {Check} from "lucide-react";
import {Button} from "@/components/ui/button";

type SuccessViewProps = {
    blob: Blob;
    projectName: string;
    onReset: () => void;
};

export default function SuccessView({blob, projectName, onReset}: SuccessViewProps) {
    const handleDownload = useCallback(() => {
        const url = URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = `${projectName}-agents.zip`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }, [blob, projectName]);

    return (
        <section className="relative flex flex-col items-center gap-6 overflow-hidden px-4 py-24 sm:py-32">
            <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
            <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />

            <div className="flex size-16 items-center justify-center rounded-full bg-emerald-900/20">
                <Check className="size-8 text-emerald-400" />
            </div>

            <h1 className="max-w-xl text-balance text-center text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
                Your AI Engineering Team is Ready
            </h1>

            <p className="max-w-lg text-balance text-center text-base leading-relaxed text-[var(--text)]">
                Download your generated agents and add them to your project.
            </p>

            <Button
                size="lg"
                className="mt-2 h-12 rounded-xl border-0 bg-emerald-900 px-8 text-base font-semibold text-white shadow-lg shadow-[var(--accent)]/25 transition-all duration-200 hover:bg-emerald-400/85 hover:shadow-[var(--accent)]/35 hover:scale-[1.02] active:scale-[0.98]"
                onClick={handleDownload}
            >
                Download ZIP
            </Button>

            <Button
                type="button"
                variant="ghost"
                onClick={onReset}
            >
                Generate another team
            </Button>
        </section>
    );
}
