import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { api } from '@/shared/api/apiClient';
import { ApiError } from '@/shared/api/errors';

const BASE = '';

beforeEach(() => {
  vi.stubGlobal('fetch', vi.fn());
});

afterEach(() => {
  vi.unstubAllGlobals();
});

describe('api.get', () => {
  it('calls fetch with GET method and correct URL', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true,
      status: 200,
      json: () => Promise.resolve({ data: 'ok' }),
    } as Response);

    await api.get('/test');

    expect(fetch).toHaveBeenCalledWith(`${BASE}/test`, expect.objectContaining({
      headers: { 'Content-Type': 'application/json' },
    }));
  });

  it('returns parsed JSON on success', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true,
      status: 200,
      json: () => Promise.resolve({ id: 1 }),
    } as Response);

    const result = await api.get<{ id: number }>('/test');

    expect(result).toEqual({ id: 1 });
  });

  it('throws ApiError on non-ok response', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: false,
      status: 404,
      json: () => Promise.resolve({ message: 'Not found' }),
    } as Response);

    await expect(api.get('/test')).rejects.toThrow(ApiError);
  });

  it('throws ApiError with parsed error body when available', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: false,
      status: 400,
      json: () => Promise.resolve({ error: 'Bad request' }),
    } as Response);

    try {
      await api.get('/test');
      expect.unreachable();
    } catch (e) {
      expect(e).toBeInstanceOf(ApiError);
      expect((e as ApiError).status).toBe(400);
      expect((e as ApiError).data).toEqual({ error: 'Bad request' });
    }
  });

  it('returns undefined on 204', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true,
      status: 204,
      json: () => Promise.resolve(),
    } as Response);

    const result = await api.get('/test');

    expect(result).toBeUndefined();
  });
});

describe('api.post', () => {
  it('calls fetch with POST method and JSON body', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true,
      status: 200,
      json: () => Promise.resolve({}),
    } as Response);

    await api.post('/test', { name: 'test' });

    expect(fetch).toHaveBeenCalledWith(`${BASE}/test`, expect.objectContaining({
      method: 'POST',
      body: JSON.stringify({ name: 'test' }),
    }));
  });

  it('serializes body to JSON', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true,
      status: 200,
      json: () => Promise.resolve({}),
    } as Response);

    const body = { foo: 'bar', num: 42 };
    await api.post('/test', body);

    expect(fetch).toHaveBeenCalledWith(`${BASE}/test`, expect.objectContaining({
      method: 'POST',
      body: JSON.stringify(body),
    }));
  });
});

describe('api.postBlob', () => {
  it('returns Blob on success', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    const blob = new Blob(['zip content'], { type: 'application/zip' });
    fetch.mockResolvedValue({
      ok: true,
      status: 200,
      blob: () => Promise.resolve(blob),
    } as Response);

    const result = await api.postBlob('/download', { id: 1 });

    expect(result).toBeInstanceOf(Blob);
  });
});

describe('api.put', () => {
  it('calls fetch with PUT method and JSON body', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true, status: 200,
      json: () => Promise.resolve({}),
    } as Response);

    await api.put('/test', { name: 'test' });

    expect(fetch).toHaveBeenCalledWith(`${BASE}/test`, expect.objectContaining({
      method: 'PUT',
      body: JSON.stringify({ name: 'test' }),
    }));
  });
});

describe('api.patch', () => {
  it('calls fetch with PATCH method and JSON body', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true, status: 200,
      json: () => Promise.resolve({}),
    } as Response);

    await api.patch('/test', { name: 'test' });

    expect(fetch).toHaveBeenCalledWith(`${BASE}/test`, expect.objectContaining({
      method: 'PATCH',
      body: JSON.stringify({ name: 'test' }),
    }));
  });
});

describe('api.delete', () => {
  it('calls fetch with DELETE method', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: true, status: 200,
      json: () => Promise.resolve({}),
    } as Response);

    await api.delete('/test');

    expect(fetch).toHaveBeenCalledWith(`${BASE}/test`, expect.objectContaining({
      method: 'DELETE',
    }));
  });
});

describe('postBlob error handling', () => {
  it('throws ApiError on non-ok response with parseable error body', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: false,
      status: 500,
      json: () => Promise.resolve({ error: 'Server error' }),
      blob: () => Promise.resolve(new Blob()),
    } as Response);

    await expect(api.postBlob('/download', {})).rejects.toThrow(ApiError);
  });

  it('throws ApiError when error body is not valid JSON', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockResolvedValue({
      ok: false,
      status: 502,
      json: () => Promise.reject(new Error('Invalid JSON')),
      blob: () => Promise.resolve(new Blob()),
    } as Response);

    await expect(api.postBlob('/download', {})).rejects.toThrow(ApiError);
  });
});

describe('network error', () => {
  it('throws on fetch rejection (network unavailable)', async () => {
    const fetch = vi.mocked(globalThis.fetch);
    fetch.mockRejectedValue(new TypeError('Failed to fetch'));

    await expect(api.get('/test')).rejects.toThrow(TypeError);
  });
});
