import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import HomePage from "./features/home/pages/HomePage.tsx";
import GenerationPage from "@/features/generation/pages/GenerationPage.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import './App.css'
import AgentsPage from "@/features/agents/pages/AgentsPage.tsx";
const queryClient = new QueryClient()
createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <QueryClientProvider client={queryClient}>
      <BrowserRouter>
          <Routes>
              <Route path={'/'} element={<HomePage></HomePage>} />
              <Route path={'/generate'} element={<GenerationPage />}/>
              <Route path={'/agents'} element={<AgentsPage />}/>
          </Routes>
      </BrowserRouter>
      </QueryClientProvider>
  </StrictMode>
)
