import { useState } from "react";
import { NavLink } from "react-router";
import { MenuIcon } from "lucide-react";
import ThemeToggle from "@/shared/theme/ThemeToggle";
import { Dialog, DialogTrigger, DialogContent } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

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
  const [mobileNavOpen, setMobileNavOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 w-full overflow-x-hidden">
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

        <nav className="hidden md:flex items-center gap-1">
          {navItems.map(({ to, label }) => (
            <NavLink key={to} to={to} className={linkClass}>
              {label}
            </NavLink>
          ))}
          <div className="ml-2 flex items-center">
            <ThemeToggle />
          </div>
        </nav>

        <Dialog open={mobileNavOpen} onOpenChange={setMobileNavOpen}>
          <DialogTrigger
            render={
              <Button variant="ghost" size="icon" className="md:hidden">
                <MenuIcon />
              </Button>
            }
          />
          <DialogContent className="top-0 left-0 h-dvh max-w-full -translate-x-0 -translate-y-0 rounded-none border-0 p-6">
            <div className="flex flex-col gap-2 pt-8">
              {navItems.map(({ to, label }) => (
                <NavLink
                  key={to}
                  to={to}
                  className={({ isActive }) =>
                    [
                      "block rounded-lg px-4 py-3 text-base font-medium transition-all duration-200",
                      isActive
                        ? "bg-[var(--accent-bg)] text-[var(--accent)]"
                        : "text-[var(--text)] hover:bg-[var(--accent-bg)]/50 hover:text-[var(--text-h)]",
                    ].join(" ")
                  }
                  onClick={() => setMobileNavOpen(false)}
                >
                  {label}
                </NavLink>
              ))}
              <div className="mt-2 border-t border-border px-4 pt-4">
                <span className="text-sm text-muted-foreground">Theme</span>
                <div className="mt-3">
                  <ThemeToggle />
                </div>
              </div>
            </div>
          </DialogContent>
        </Dialog>
      </div>
    </header>
  );
}