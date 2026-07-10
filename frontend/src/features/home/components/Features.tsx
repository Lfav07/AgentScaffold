const features = [
  {
    title: "Specialized Agents",
    description:
      "Generate expert AI agents tailored to your stack and engineering practices.",
  },
  {
    title: "Opinionated Presets",
    description:
      "Apply curated architectures and engineering conventions in a single click.",
  },
  {
    title: "Consistent Team",
    description:
      "Every generated agent shares the same stack, architecture, and coding standards.",
  },
  {
    title: "Portable Output",
    description:
      "Download a ready-to-use zip compatible with your favorite AI coding tools.",
  },
];

export default function Features() {
  return (
    <section className="border-t border-[var(--border)] py-20 sm:py-28">
      <div className="mx-auto max-w-5xl px-4">
        <h2 className="text-center text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
          Built for Real Workflows
        </h2>
        <p className="mt-3 text-center text-[var(--text)]">
          Everything you need to scaffold AI agents at scale
        </p>

        <div className="mt-16 grid gap-4 sm:grid-cols-2">
          {features.map((feature, i) => (
            <div
              key={i}
              className="group rounded-xl border border-[var(--border)] bg-[var(--bg)]/50 p-6 text-left transition-all duration-200 hover:border-emerald-900/50 hover:bg-emerald-950/10"
            >
              <h3 className="text-base font-semibold text-[var(--text-h)] transition-colors duration-200 group-hover:text-emerald-400">
                {feature.title}
              </h3>
              <p className="mt-2 text-sm leading-relaxed text-[var(--text)]">
                {feature.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
