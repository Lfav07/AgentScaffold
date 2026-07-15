import {useQuery} from "@tanstack/react-query";
import {getStacks} from "@/features/stacks/api/stacksApi.ts";

export function useStacks(){
    return useQuery({queryKey: ['stacks'], queryFn: getStacks, staleTime:  5 * 60_000})
}