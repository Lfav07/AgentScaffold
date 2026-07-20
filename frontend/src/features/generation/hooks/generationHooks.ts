import {useMutation} from "@tanstack/react-query";
import {generate} from "@/features/generation/api/generationApi.ts";

export function useGeneration() {
    return useMutation({
        mutationFn: (vars, { signal }) => generate(vars, { signal }),
    });
}