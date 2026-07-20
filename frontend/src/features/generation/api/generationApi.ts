import {api} from "@/shared/api";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";


export async function generate(request: GenerationRequestType, { signal }: { signal?: AbortSignal } = {}): Promise<Blob>{
    return api.postBlob("/scaffold", request, signal)
}


