import {api} from "@/shared/api";
import type {Agent, Preset, Stack} from "@/shared/types.ts";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";

export async function getPresets(): Promise<Preset[]>{
    return api.get<Preset[]>("/presets")
}

export async function getAgents(): Promise<Agent[]>{
    return api.get<Agent[]>("/agents")
}

export async function getStacks(): Promise<Stack[]>{
    return api.get<Stack[]>("/stacks")
}
export async function generate(request: GenerationRequestType): Promise<Blob>{
    return api.postBlob("/generate", request)
}


