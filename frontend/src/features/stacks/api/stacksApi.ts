import type {Stack} from "@/shared/types.ts";
import {api} from "@/shared/api";

export async function getStacks(): Promise<Stack[]>{
    return api.get<Stack[]>("/stacks")
}