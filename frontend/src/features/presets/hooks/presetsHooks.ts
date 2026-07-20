import {useQuery} from "@tanstack/react-query";
import {getPresets} from "@/features/presets/api/presetsApi.ts";

export function usePresets(){
    return useQuery({queryKey: ['presets'], queryFn: ({ signal }) => getPresets({ signal }), staleTime:  5 * 60_000})
}