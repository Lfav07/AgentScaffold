import {useQuery} from "@tanstack/react-query";
import {getPresets} from "@/features/presets/api/presetsApi.ts";

export function usePresets(){
    return useQuery({queryKey: ['presets'], queryFn: getPresets, staleTime:  5 * 60_000})
}