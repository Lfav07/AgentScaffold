import { NavLink } from "react-router";

const navItems = [
  { to: "/", label: "Home" },
  { to: "/generate", label: "Generate" },
  { to: "/presets", label: "Presets" },
  { to: "/agents", label: "Agents" },
  { to: "/stacks", label: "Stacks" },
];

const linkClass = ({ isActive }: { isActive: boolean }) =>
  [
    "relative px-3 py-1.5 text-sm font-medium transition-all duration-200 rounded-md",
    isActive
      ? "text-[var(--accent)] bg-[var(--accent-bg)]"
      : "text-[var(--text)] hover:text-[var(--text-h)] hover:bg-[var(--accent-bg)]/50",
  ].join(" ");

export default function Header() {
  return (
    <header className="sticky top-0 z-50 w-full border-b border-[var(--border)]">
      <div className="absolute inset-0 bg-[var(--bg)]/80 backdrop-blur-xl" />
      <div className="absolute inset-x-0 top-0 h-px bg-gradient-to-r from-transparent via-[var(--accent)]/50 to-transparent" />

      <div className="relative mx-auto flex h-14 max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8">
        <span className="flex items-center gap-2.5 text-base font-semibold tracking-tight text-[var(--text-h)]">
          <span className="flex size-7 items-center justify-center rounded-lg bg-[var(--accent)]/10 ring-1 ring-[var(--accent)]/20">
            <svg
              className="size-4 text-[var(--accent)]"
              viewBox="0 0 20 20"
              fill="currentColor"
            >
              <path
                d="M10 2L18 10L10 18L2 10L10 2Z"
                stroke="currentColor"
                fill="none"
                strokeWidth="1.5"
                strokeLinejoin="round"
              />
              <path
                d="M10 6L14 10L10 14L6 10L10 6Z"
                fill="currentColor"
                opacity="0.5"
              />
            </svg>
          </span>
          AgentScaffold
        </span>

        <nav className="flex items-center gap-1">
          {navItems.map(({ to, label }) => (
            <NavLink key={to} to={to} className={linkClass}>
              {label}
            </NavLink>
          ))}
        </nav>
      </div>
    </header>
  );
}