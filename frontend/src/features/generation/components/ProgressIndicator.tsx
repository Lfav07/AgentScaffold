
import {
    Progress,
    ProgressLabel,
} from "@/components/ui/progress"
import type {formSteps} from "@/features/generation/types.ts";

export default  function ProgressIndicator({step}: formSteps){
    return (
        <Progress value={step / 3 * 100}>
            <ProgressLabel></ProgressLabel>
            <h1>Step {step + 1} of 4</h1>
        </Progress>
    )
}