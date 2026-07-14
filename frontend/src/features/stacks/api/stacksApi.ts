import {api} from "@/shared/api";
import type {StackResponse} from "@/features/stacks/types.ts";

export async function getStacks(): Promise<StackResponse>{
    return api.get<StackResponse>("/stacks")
}