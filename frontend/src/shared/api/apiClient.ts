import { ApiError, TimeoutError } from "./errors";

const API_BASE_URL = import.meta.env.VITE_API_URL;
const REQUEST_TIMEOUT = 30_000;

function withTimeout<T>(promise: Promise<T>, ms: number): Promise<T> {
    return Promise.race([
        promise,
        new Promise<T>((_, reject) =>
            setTimeout(() => reject(new TimeoutError(ms)), ms),
        ),
    ]);
}

async function request<T>(
    endpoint: string,
    init?: RequestInit,
): Promise<T> {
    const response = await withTimeout(
        fetch(`${API_BASE_URL}${endpoint}`, {
            headers: {
                "Content-Type": "application/json",
                ...init?.headers,
            },
            ...init,
        }),
        REQUEST_TIMEOUT,
    );

    if (!response.ok) {
        let errorData: unknown = null;

        try {
            errorData = await response.json();
        } catch {
            // Ignore non-JSON error responses
        }

        throw new ApiError(response.status, errorData);
    }

    if (response.status === 204) {
        return undefined as T;
    }

    return response.json() as Promise<T>;
}

async function requestBlob(
    endpoint: string,
    init?: RequestInit,
): Promise<Blob> {
    const response = await withTimeout(
        fetch(`${API_BASE_URL}${endpoint}`, {
            headers: {
                "Content-Type": "application/json",
                ...init?.headers,
            },
            ...init,
        }),
        REQUEST_TIMEOUT,
    );

    if (!response.ok) {
        let errorData: unknown = null;

        try {
            errorData = await response.json();
        } catch {
            // Ignore non-JSON error responses
        }

        throw new ApiError(response.status, errorData);
    }

    return response.blob();
}

export const api = {
    get: <T>(url: string, signal?: AbortSignal) =>
        request<T>(url, { signal }),

    post: <T>(url: string, body?: unknown, signal?: AbortSignal) =>
        request<T>(url, {
            method: "POST",
            body: body ? JSON.stringify(body) : undefined,
            signal,
        }),

    postBlob: (url: string, body?: unknown, signal?: AbortSignal) =>
        requestBlob(url, {
            method: "POST",
            body: body ? JSON.stringify(body) : undefined,
            signal,
        }),

    put: <T>(url: string, body?: unknown, signal?: AbortSignal) =>
        request<T>(url, {
            method: "PUT",
            body: body ? JSON.stringify(body) : undefined,
            signal,
        }),

    patch: <T>(url: string, body?: unknown, signal?: AbortSignal) =>
        request<T>(url, {
            method: "PATCH",
            body: body ? JSON.stringify(body) : undefined,
            signal,
        }),

    delete: <T>(url: string, signal?: AbortSignal) =>
        request<T>(url, {
            method: "DELETE",
            signal,
        }),
};