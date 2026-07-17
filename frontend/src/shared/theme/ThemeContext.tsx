import { createContext, useCallback, useEffect, useState } from 'react';
import type { ThemeMode, ResolvedTheme, ThemeContextValue } from './types';

const STORAGE_KEY = 'theme-mode';

function getInitialMode(): ThemeMode {
  try {
    const stored = localStorage.getItem(STORAGE_KEY);
    if (stored === 'light' || stored === 'dark' || stored === 'system') {
      return stored;
    }
  } catch {
    // localStorage unavailable
  }
  return 'system';
}

function resolveTheme(mode: ThemeMode): ResolvedTheme {
  if (mode === 'system') {
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }
  return mode;
}

function syncClass(resolved: ResolvedTheme) {
  document.documentElement.classList.toggle('dark', resolved === 'dark');
}

function persistMode(mode: ThemeMode) {
  try {
    localStorage.setItem(STORAGE_KEY, mode);
  } catch {
    // localStorage unavailable
  }
}

export const ThemeContext = createContext<ThemeContextValue | null>(null);

interface ThemeProviderProps {
  children: React.ReactNode;
}

export function ThemeProvider({ children }: ThemeProviderProps) {
  const [mode, setModeState] = useState<ThemeMode>(getInitialMode);
  const [resolved, setResolved] = useState<ResolvedTheme>(() => resolveTheme(mode));

  const setMode = useCallback((next: ThemeMode) => {
    setModeState(next);
    persistMode(next);
    const nextResolved = resolveTheme(next);
    setResolved(nextResolved);
    syncClass(nextResolved);
  }, []);

  useEffect(() => {
    const nextResolved = resolveTheme(mode);
    setResolved(nextResolved);
    syncClass(nextResolved);
  }, [mode]);

  useEffect(() => {
    if (mode !== 'system') return;

    const mql = window.matchMedia('(prefers-color-scheme: dark)');
    const onChange = () => {
      const nextResolved = resolveTheme('system');
      setResolved(nextResolved);
      syncClass(nextResolved);
    };
    mql.addEventListener('change', onChange);
    return () => mql.removeEventListener('change', onChange);
  }, [mode]);

  return (
    <ThemeContext.Provider value={{ mode, resolved, setMode }}>
      {children}
    </ThemeContext.Provider>
  );
}
