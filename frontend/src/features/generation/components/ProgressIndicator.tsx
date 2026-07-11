
import {
    Progress,
    ProgressLabel,
} from "@/components/ui/progress"
import type {formSteps} from "@/features/generation/types.ts";

export default  function ProgressIndicator({step}: formSteps){
    return (
        <Progress value={step / 4 * 100}>
            <ProgressLabel></ProgressLabel>
            <h1>Steps Completed: {step} / 4</h1>
        </Progress>
    )
}