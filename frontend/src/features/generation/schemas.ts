import * as z from "zod"

export const PresetEnum = z.enum([
    "enterprise-fullstack",
    "enterprise-spring",
    "enterprise-react",
    "startup-ready",
    "spring-ready",
    "react-ready"
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
)
export const stepFields: Record<number, (keyof GenerationRequestType)[]> = {
    0: ["projectName"],
    1: ["preset"],
    2: ["backendStack"],
    3: ["frontendStack"]
} as const;
export type GenerationRequestType = z.infer<typeof generationRequestSchema>;