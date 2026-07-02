import {NavLink} from "react-router";

const linkClass = ({isActive}: { isActive: boolean }) =>
    `text-sm font-medium transition-colors duration-150 ${
        isActive
            ? "text-white"
            : "text-white/70 hover:text-white"
    }`;

export default function Header() {
    return (
        <header className="sticky top-0 z-50 w-full border-b border-white/10 bg-gray-900/80 backdrop-blur-md">
            <div className="mx-auto flex h-14 max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8">
                <span className="text-lg font-semibold tracking-tight text-white">AgentScaffold</span>
                <nav className="flex items-center justify-items-cn gap-5">
                    <NavLink to="/Home" className={linkClass}>Home</NavLink>
                    <NavLink to="/generate" className={linkClass}>Generate</NavLink>
                    <NavLink to="/presets" className={linkClass}>Presets</NavLink>
                    <NavLink to="/agents" className={linkClass}>Agents</NavLink>
                    <NavLink to="/stacks" className={linkClass}>Stacks</NavLink>
                </nav>
            </div>
        </header>
    );
}