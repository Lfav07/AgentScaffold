import {api} from "@/shared/api";
import type {Preset} from "@/shared/types.ts";

export async function  getPresets({ signal }: { signal?: AbortSignal } = {}): Promise<Preset[]>{
    return api.get<Preset[]>("/presets", signal)
}