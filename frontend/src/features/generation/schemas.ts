import * as z from "zod"

const PresetEnum = z.enum([
    "enterprise-fullstack",
    "enterprise-spring",
    "enterprise-react",
    "startup-ready",
    "spring-ready",
    "react-ready"
])
const BackendStackEnum = z.enum([
    "java-spring"
])
const FrontendStackEnum = z.enum([
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

export type GenerationRequestType = z.infer<typeof generationRequestSchema>;