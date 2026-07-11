import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
// @ts-ignore
import App from './App.tsx'
import {BrowserRouter, Route, Routes} from "react-router";
import HomePage from "./features/home/pages/HomePage.tsx";
import GenerationPage from "@/features/generation/pages/GenerationPage.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path={'/'} element={<HomePage></HomePage>} />
              <Route path={'/generate'} element={<GenerationPage />}/>
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)
