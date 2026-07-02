import {NavLink} from "react-router";

export default function Header(){
    return <div className={"bg-blue-500 flex justify-center content-center"}>
        <nav className={"flex gap-6"}>
            <NavLink to={"/Home"}>Home</NavLink>
            <NavLink to={"/generate"}>Generate</NavLink>
            <NavLink to={"/presets"}>Presets</NavLink>
            <NavLink to={"/agents"}>Agents</NavLink>
            <NavLink to={"/stacks"}>Stacks</NavLink>
        </nav>
    </div>
}