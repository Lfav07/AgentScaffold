import { describe, it, expect, vi, beforeEach } from 'vitest';
import { renderHook, waitFor } from '@testing-library/react';
import { useGeneration } from '@/features/generation/hooks/generationHooks';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import type { ReactNode } from 'react';

const mockGenerate = vi.fn();

vi.mock('@/features/generation/api/generationApi', () => ({
  generate: (payload: unknown) => mockGenerate(payload),
}));

function createWrapper() {
  const queryClient = new QueryClient({
    defaultOptions: { queries: { retry: false }, mutations: { retry: false } },
  });
  return ({ children }: { children: ReactNode }) => (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
  );
}

beforeEach(() => {
  mockGenerate.mockReset();
});

describe('useGeneration', () => {
  it('calls generate API with correct payload on mutate', async () => {
    mockGenerate.mockResolvedValueOnce(new Blob());
    const { result } = renderHook(() => useGeneration(), { wrapper: createWrapper() });

    result.current.mutate({ presetKey: 'startup-ready', projectName: 'test' });

    await waitFor(() => {
      expect(mockGenerate).toHaveBeenCalledWith({ presetKey: 'startup-ready', projectName: 'test' });
    });
  });

  it('transitions to isPending during mutation', async () => {
    mockGenerate.mockImplementation(() => new Promise(() => {}));
    const { result } = renderHook(() => useGeneration(), { wrapper: createWrapper() });

    result.current.mutate({ presetKey: 'startup-ready', projectName: 'test' });

    await waitFor(() => {
      expect(result.current.isPending).toBe(true);
    });
  });

  it('transitions to isSuccess and returns Blob on success', async () => {
    const blob = new Blob(['test'], { type: 'application/zip' });
    mockGenerate.mockResolvedValueOnce(blob);
    const { result } = renderHook(() => useGeneration(), { wrapper: createWrapper() });

    result.current.mutate({ presetKey: 'startup-ready', projectName: 'test' });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
      expect(result.current.data).toBe(blob);
    });
  });

  it('transitions to error and exposes Error on failure', async () => {
    mockGenerate.mockRejectedValue(new Error('Generation failed'));
    const { result } = renderHook(() => useGeneration(), { wrapper: createWrapper() });

    result.current.mutate({ presetKey: 'startup-ready', projectName: 'test' });

    await waitFor(() => {
      expect(result.current.isError).toBe(true);
      expect(result.current.error).toBeDefined();
    }, { timeout: 5000 });
  });

  it('reset() clears the mutation state', async () => {
    mockGenerate.mockResolvedValueOnce(new Blob());
    const { result } = renderHook(() => useGeneration(), { wrapper: createWrapper() });

    result.current.mutate({ presetKey: 'startup-ready', projectName: 'test' });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    result.current.reset();

    await waitFor(() => {
      expect(result.current.isPending).toBe(false);
      expect(result.current.isSuccess).toBe(false);
      expect(result.current.data).toBeUndefined();
    });
  });
});
