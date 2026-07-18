import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { render, screen, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ThemeProvider, ThemeContext } from '@/shared/theme/ThemeContext';
import { useContext } from 'react';

let matchMediaListeners: Array<() => void> = [];
let prefersDark = false;

function TestConsumer() {
  const ctx = useContext(ThemeContext);
  if (!ctx) return <div>no context</div>;
  return (
    <div>
      <span data-testid="mode">{ctx.mode}</span>
      <span data-testid="resolved">{ctx.resolved}</span>
      <button data-testid="set-light" onClick={() => ctx.setMode('light')}>Light</button>
      <button data-testid="set-dark" onClick={() => ctx.setMode('dark')}>Dark</button>
      <button data-testid="set-system" onClick={() => ctx.setMode('system')}>System</button>
    </div>
  );
}

function renderWithProvider() {
  return render(
    <ThemeProvider>
      <TestConsumer />
    </ThemeProvider>
  );
}

beforeEach(() => {
  localStorage.clear();
  matchMediaListeners = [];
  prefersDark = false;
  Object.defineProperty(window, 'matchMedia', {
    writable: true,
    value: vi.fn().mockImplementation((query: string) => ({
      matches: query === '(prefers-color-scheme: dark)' ? prefersDark : false,
      media: query,
      addEventListener: (_event: string, handler: () => void) => {
        matchMediaListeners.push(handler);
      },
      removeEventListener: vi.fn(),
    })),
  });
  document.documentElement.classList.remove('dark');
});

afterEach(() => {
  vi.unstubAllGlobals();
});

describe('ThemeProvider', () => {
  it('defaults to system mode when localStorage is empty', () => {
    renderWithProvider();
    expect(screen.getByTestId('mode').textContent).toBe('system');
  });

  it('reads persisted mode from localStorage on mount', () => {
    localStorage.setItem('theme-mode', 'dark');
    renderWithProvider();
    expect(screen.getByTestId('mode').textContent).toBe('dark');
    expect(screen.getByTestId('resolved').textContent).toBe('dark');
  });

  it('setMode dark resolves to dark and persists', async () => {
    const user = userEvent.setup();
    renderWithProvider();
    await user.click(screen.getByTestId('set-dark'));
    expect(screen.getByTestId('mode').textContent).toBe('dark');
    expect(screen.getByTestId('resolved').textContent).toBe('dark');
    expect(localStorage.getItem('theme-mode')).toBe('dark');
  });

  it('setMode light resolves to light and persists', async () => {
    const user = userEvent.setup();
    renderWithProvider();
    await user.click(screen.getByTestId('set-light'));
    expect(screen.getByTestId('mode').textContent).toBe('light');
    expect(screen.getByTestId('resolved').textContent).toBe('light');
    expect(localStorage.getItem('theme-mode')).toBe('light');
  });

  it('toggles dark class on document.documentElement', async () => {
    const user = userEvent.setup();
    renderWithProvider();
    await user.click(screen.getByTestId('set-dark'));
    expect(document.documentElement.classList.contains('dark')).toBe(true);
  });

  it('removes dark class when switching to light', async () => {
    const user = userEvent.setup();
    renderWithProvider();
    await user.click(screen.getByTestId('set-dark'));
    expect(document.documentElement.classList.contains('dark')).toBe(true);
    await user.click(screen.getByTestId('set-light'));
    expect(document.documentElement.classList.contains('dark')).toBe(false);
  });

  it('handles localStorage errors gracefully', () => {
    vi.stubGlobal('localStorage', {
      getItem: () => { throw new Error('quota exceeded'); },
      setItem: () => { throw new Error('quota exceeded'); },
      removeItem: () => {},
      clear: () => {},
      length: 0,
    });
    renderWithProvider();
    expect(screen.getByTestId('mode').textContent).toBe('system');
  });
});

describe('system preference', () => {
  it('resolves to dark when prefers-color-scheme: dark', () => {
    prefersDark = true;
    renderWithProvider();
    expect(screen.getByTestId('resolved').textContent).toBe('dark');
  });

  it('resolves to light when prefers-color-scheme: light', () => {
    prefersDark = false;
    renderWithProvider();
    expect(screen.getByTestId('resolved').textContent).toBe('light');
  });

  it('responds to system preference changes while in system mode', () => {
    prefersDark = false;
    renderWithProvider();
    expect(screen.getByTestId('resolved').textContent).toBe('light');

    prefersDark = true;
    act(() => { matchMediaListeners.forEach(fn => fn()); });

    expect(screen.getByTestId('resolved').textContent).toBe('dark');
  });
});
