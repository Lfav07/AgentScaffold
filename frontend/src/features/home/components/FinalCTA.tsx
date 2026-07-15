import { Button } from "@/components/ui/button";
import {useNavigate} from "react-router";

export default function FinalCTA() {
    const navigate = useNavigate();
    return (
    <section  className="relative flex flex-col items-center gap-6 overflow-hidden  px-4 py-20 sm:py-28">
      <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.03] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
      <div className="pointer-events-none absolute -bottom-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />

      <h2 className="max-w-xl text-balance text-center text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
        Ready to build your AI engineering team?
      </h2>
      <p className="max-w-lg text-balance text-center text-[var(--text)]">
        Pick a preset, choose your stack, and get a production-ready team of AI agents in seconds.
      </p>

      <Button
        size="lg"
        className="mt-2 h-12 rounded-xl border-0 bg-emerald-900 px-8 text-base font-semibold text-white shadow-lg shadow-[var(--accent)]/25 transition-all duration-200 hover:bg-emerald-400/85 hover:shadow-[var(--accent)]/35 hover:scale-[1.02] active:scale-[0.98]"
        onClick={() => navigate("/generate")}
      >
        Generate your specialized AI team
      </Button>
    </section>
  );
}
