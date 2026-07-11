import ProgressIndicator from "./ProgressIndicator";


export default function MultiStepForm(){
    return (
        <div className={"w-screen mx-auto h-200 bg-red-200 flex m-10 flex-col items-center align-center"}>
            <ProgressIndicator step={1}></ProgressIndicator>
            <h2>Future multi step form here!</h2>
        </div>
    );
}