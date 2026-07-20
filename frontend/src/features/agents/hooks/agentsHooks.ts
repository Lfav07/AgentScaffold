import {useQuery} from "@tanstack/react-query";
import {getAgents} from "@/features/agents/api/agentsApi.ts";

export function useAgents(){
    return useQuery({queryKey: ['agents'], queryFn: ({ signal }) => getAgents({ signal }), staleTime:  5 * 60_000})
}