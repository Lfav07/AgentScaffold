import { Button } from "@/components/ui/button";
import {useNavigate} from "react-router";

export default function Hero() {
    let navigate = useNavigate();
  return (
    <section className="relative flex flex-col items-center gap-6 overflow-hidden px-4 py-24 sm:py-32">
      <div className="pointer-events-none absolute inset-0 bg-[length:32px_32px] opacity-[0.04] [background-image:radial-gradient(circle,var(--accent)_1px,transparent_1px)]" />
      <div className="pointer-events-none absolute -top-48 left-1/2 size-[32rem] -translate-x-1/2 rounded-full bg-[var(--accent)]/8 blur-3xl" />

      <span className="inline-flex items-center gap-1.5 rounded-full border border-[var(--accent-border)] bg-teal-900 px-3 py-1 text-xs font-medium text-[var(--accent)]">
        <span className="size-1.5 rounded-full bg-current" />
        AI Agent Scaffolding
      </span>

      <h1 className="max-w-3xl text-balance text-center text-4xl font-bold tracking-tight text-[var(--text-h)] sm:text-5xl lg:text-6xl">
        Generate a Fully Configured{" "}
        <span className="">AI Engineering Team</span>
      </h1>

      <p className="max-w-2xl text-balance text-center text-base leading-relaxed text-[var(--text)] sm:text-lg">
        Choose a preset, define your technology stack, and generate a ready-to-use AI engineering team for Claude, Codex, OpenCode, and any coding tool that supports agent skills.
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
