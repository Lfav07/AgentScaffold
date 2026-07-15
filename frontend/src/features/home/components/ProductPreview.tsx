export default function ProductPreview() {
    return (
        <section className="mx-auto max-w-5xl px-4 py-20 sm:py-28">
            <h1 className="mb-12 text-center text-3xl font-bold tracking-tight text-[var(--text-h)] sm:text-4xl">
                Output Preview
            </h1>
            <div
                className="rounded-2xl border border-[var(--accent-border)] bg-[var(--bg)] shadow-lg shadow-[var(--accent)]/10">
                {/* Terminal header */}
                <div
                    className="flex items-center gap-2 rounded-t-2xl border-b border-[var(--accent-border)] bg-[var(--accent)]/5 px-5 py-3">
                    <span className="size-3 rounded-full bg-red-400"/>
                    <span className="size-3 rounded-full bg-yellow-400"/>
                    <span className="size-3 rounded-full bg-green-400"/>
                    <span className="ml-2 text-xs font-medium text-[var(--text)]/60">
                        preview — generated project
                    </span>
                </div>

                {/* Terminal body */}
                <div className="overflow-x-auto p-5 font-mono text-sm leading-relaxed text-[var(--text)]">
                    <pre className="text-[var(--text)]/80">

{`  
                       ✦ Generated Project ✦                     
                                                                  
     📦 demo-agents.zip                                          
                                                                 
     📁 agents/                                                   
       🤖 backend-architect.md                                   
       🤖 backend-reviewer.md                                    
       🤖 frontend-implementer.md                                 
       🤖 backend-tester.md                                      
       🤖 final-reviewer.md                                      
                                                                 

                       ✔ Ready to Download                       
`}
                    </pre>
                </div>
            </div>
        </section>
    );
}