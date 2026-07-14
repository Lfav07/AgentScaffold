
import {
    Progress,
    ProgressLabel,
} from "@/components/ui/progress"
import type {formSteps} from "@/features/generation/types.ts";

export default function ProgressIndicator({step, totalSteps = 3}: formSteps){
    return (
        <Progress value={step / totalSteps * 100}>
            <ProgressLabel />
            <h1>Step {step + 1} of {totalSteps}</h1>
        </Progress>
    )
}