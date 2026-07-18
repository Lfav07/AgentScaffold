import type { Command } from './types';
import type { NavigateFunction } from 'react-router';
import type { ThemeMode } from '@/shared/theme/types';
import { Home, Sparkles, LayoutDashboard, Users, Layers, Sun, Moon, Monitor } from 'lucide-react';

export function createCommands(
  navigate: NavigateFunction,
  setTheme: (mode: ThemeMode) => void
): Command[] {
  return [
    { id: 'nav-home', label: 'Home', description: 'Go to home page', keywords: ['home', 'landing', 'start'], category: 'Navigation', onSelect: () => navigate('/'), icon: Home },
    { id: 'nav-generate', label: 'Generate', description: 'Create a new project scaffold', keywords: ['generate', 'create', 'scaffold', 'new'], category: 'Navigation', onSelect: () => navigate('/generate'), icon: Sparkles },
    { id: 'nav-presets', label: 'Presets', description: 'Browse project presets', keywords: ['presets', 'templates', 'boilerplate'], category: 'Navigation', onSelect: () => navigate('/presets'), icon: LayoutDashboard },
    { id: 'nav-agents', label: 'Agents', description: 'View available agents', keywords: ['agents', 'ai', 'assistants'], category: 'Navigation', onSelect: () => navigate('/agents'), icon: Users },
    { id: 'nav-stacks', label: 'Stacks', description: 'Browse technology stacks', keywords: ['stacks', 'tech', 'technology', 'backend', 'frontend'], category: 'Navigation', onSelect: () => navigate('/stacks'), icon: Layers },
    { id: 'action-theme-dark', label: 'Switch to dark mode', description: 'Change theme to dark', keywords: ['theme', 'dark', 'mode', 'appearance'], category: 'Actions', onSelect: () => setTheme('dark'), icon: Moon },
    { id: 'action-theme-light', label: 'Switch to light mode', description: 'Change theme to light', keywords: ['theme', 'light', 'mode', 'appearance'], category: 'Actions', onSelect: () => setTheme('light'), icon: Sun },
    { id: 'action-theme-system', label: 'Use system theme', description: 'Follow system preference', keywords: ['theme', 'system', 'auto', 'appearance'], category: 'Actions', onSelect: () => setTheme('system'), icon: Monitor },
  ];
}
