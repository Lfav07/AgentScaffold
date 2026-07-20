import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import HomePage from "./features/home/pages/HomePage.tsx";
import GenerationPage from "@/features/generation/pages/GenerationPage.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import './App.css'
import AgentsPage from "@/features/agents/pages/AgentsPage.tsx";
import PresetsPage from "@/features/presets/pages/PresetsPage.tsx";
import StacksPage from "@/features/stacks/pages/StacksPage.tsx";
import { ThemeProvider } from '@/shared/theme/ThemeContext';
import { CommandPaletteProvider } from '@/features/command-palette/CommandPaletteContext';
const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            retry: 3,
            retryDelay: (attemptIndex: number) => Math.min(1000 * 2 ** attemptIndex, 30000),
        },
        mutations: {
            retry: 3,
            retryDelay: (attemptIndex: number) => Math.min(1000 * 2 ** attemptIndex, 30000),
        },
    },
})
createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ThemeProvider>
      <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <CommandPaletteProvider>
          <Routes>
              <Route path={'/'} element={<HomePage></HomePage>} />
              <Route path={'/generate'} element={<GenerationPage />}/>
              <Route path={'/agents'} element={<AgentsPage />}/>
              <Route path={'/presets'} element={<PresetsPage />} />
              <Route path={'/stacks'} element={<StacksPage />} />
          </Routes>
        </CommandPaletteProvider>
      </BrowserRouter>
      </QueryClientProvider>
    </ThemeProvider>
  </StrictMode>
)
