import {useMutation} from "@tanstack/react-query";
import {generate} from "@/features/generation/api/generationApi.ts";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";

export function useGeneration() {
    return useMutation<Blob, Error, GenerationRequestType>({
        mutationFn: (vars) => generate(vars),
    });
}