import {Button} from "@/components/ui/button";

type ErrorViewProps = {
    message: string;
    onRetry?: () => void;
};

export default function ErrorView({message, onRetry}: ErrorViewProps) {
    return (
        <section className="relative flex flex-col items-center justify-center gap-6 px-4 py-24 sm:py-32">
            <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
            <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />
            <p className="text-base text-red-400">{message}</p>
            {onRetry && (
                <Button
                    size="lg"
                    className="h-10 rounded-xl border-0 bg-emerald-900 px-6 text-sm font-semibold text-white shadow-lg shadow-[var(--accent)]/25 transition-all duration-200 hover:bg-emerald-400/85 hover:shadow-[var(--accent)]/35 hover:scale-[1.02] active:scale-[0.98]"
                    onClick={onRetry}
                >
                    Try again
                </Button>
            )}
        </section>
    );
}
