import {useFormContext} from "react-hook-form";
import type {GenerationRequestType} from "@/features/generation/schemas.ts";

export default function ProjectNameStep(){
    const {register, formState} = useFormContext<GenerationRequestType>()
return(
  <div>
   <h1>Step 1: Project name</h1>
      <h2>Insert the name of your project below. The project name shall be used to customize the agents generated.</h2>
      <label>
          Project Name
          <input {...register("projectName")} />
      </label>
  </div>
);
}