const steps = [
  {
    number: "01",
    title: "Choose a Preset",
    description:
      "Choose from curated presets, from enterprise architectures to lightweight single-stack projects. Whatever best fits your project.",
  },
  {
    number: "02",
    title: "Select Your Stacks",
    description:
      "Choose your technology stack. Every generated agent is customized for your frameworks, tools, and conventions.",
  },
  {
    number: "03",
    title: "Download & Use",
    description:
      "Download a ready-to-use ZIP, copy the generated agents into your project, and start working with production-ready AI specialists.",
  },
];

export default function HowItWorks() {
  return (
    <section className="py-20 sm:py-28">
      <div className="mx-auto max-w-5xl px-4">
        <h2 className="text-center text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
          How It Works
        </h2>
        <p className="mt-3 text-center text-[var(--text)]">
          Three steps to a fully configured engineering team
        </p>

        <div className="relative mt-16 grid gap-12 sm:grid-cols-3 sm:gap-8">
          {steps.map((step, i) => (
            <div key={i} className="relative flex flex-col items-center text-center">
              <span className="flex size-12 items-center justify-center rounded-xl bg-black text-lg font-bold text-[var(--accent)] ring-1 ring-[var(--accent-border)]">
                {step.number}
              </span>
              <h3 className="mt-5 text-lg font-semibold text-[var(--text-h)]">
                {step.title}
              </h3>
              <p className="mt-2 text-sm leading-relaxed text-[var(--text)]">
                {step.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
