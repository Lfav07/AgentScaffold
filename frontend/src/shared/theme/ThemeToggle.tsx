import { Monitor, Moon, Sun } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { useTheme } from './useTheme';
import type { ThemeMode } from './types';

const options: { mode: ThemeMode; icon: typeof Sun; label: string }[] = [
  { mode: 'light', icon: Sun, label: 'Light mode' },
  { mode: 'dark', icon: Moon, label: 'Dark mode' },
  { mode: 'system', icon: Monitor, label: 'System mode' },
];

export default function ThemeToggle() {
  const { mode, setMode } = useTheme();

  return (
    <div role="radiogroup" aria-label="Theme mode" className="flex items-center gap-0.5">
      {options.map(({ mode: optMode, icon: Icon, label }) => (
        <Button
          key={optMode}
          variant={mode === optMode ? 'default' : 'ghost'}
          size="icon-sm"
          role="radio"
          aria-checked={mode === optMode}
          aria-label={label}
          onClick={() => setMode(optMode)}
        >
          <Icon />
        </Button>
      ))}
    </div>
  );
}
