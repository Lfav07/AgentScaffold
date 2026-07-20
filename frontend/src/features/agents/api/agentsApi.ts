import type {Agent} from "@/shared/types.ts";
import {api} from "@/shared/api";

export async function getAgents({ signal }: { signal?: AbortSignal } = {}): Promise<Agent[]>{
    return api.get<Agent[]>("/agents", signal)
}
