import * as z from "zod"
import {presetStackRequirements} from "@/features/generation/presetStacks.ts";

export const PresetEnum = z.enum([
    "enterprise-fullstack",
    "enterprise-backend",
    "enterprise-frontend",
    "startup-ready",
    "backend-ready",
    "frontend-ready"
])
export const BackendStackEnum = z.enum([
    "java-spring"
])
export const FrontendStackEnum = z.enum([
    "typescript-react"
])


export const generationRequestSchema = z.object(
    {
        preset: PresetEnum,
        projectName: z.string()
            .trim()
            .min(1, "Project name is required"),
        backendStack: z.optional(BackendStackEnum),
        frontendStack: z.optional(FrontendStackEnum)
    }
).superRefine((data, ctx) => {
    const reqs = presetStackRequirements[data.preset];
    if (reqs?.backend && !data.backendStack) {
        ctx.addIssue({ code: z.ZodIssueCode.custom, path: ["backendStack"], message: "Backend stack is required for this preset" });
    }
    if (reqs?.frontend && !data.frontendStack) {
        ctx.addIssue({ code: z.ZodIssueCode.custom, path: ["frontendStack"], message: "Frontend stack is required for this preset" });
    }
});
export const stepFields: Record<number, (keyof GenerationRequestType)[]> = {
    0: ["projectName"],
    1: ["preset"],
    2: ["backendStack", "frontendStack"],
    3: [],
} as const;
export type GenerationRequestType = z.infer<typeof generationRequestSchema>;