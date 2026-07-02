import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
// @ts-ignore
import App from './App.tsx'
import {BrowserRouter, Route, Routes} from "react-router";
import HomePage from "./features/home/pages/HomePage.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route index element={<HomePage></HomePage>}></Route>
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)
