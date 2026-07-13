import type {Agent} from "@/shared/types.ts";
import {api} from "@/shared/api";

export async function getAgents(): Promise<Agent[]>{
    return api.get<Agent[]>("/agents")
}
