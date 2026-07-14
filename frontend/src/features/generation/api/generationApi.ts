import {api} from "@/shared/api";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";


export async function generate(request: GenerationRequestType): Promise<Blob>{
    return api.postBlob("/scaffold", request)
}


