import { ApiError } from "./errors";

const API_BASE_URL = import.meta.env.VITE_API_URL;

async function request<T>(
    endpoint: string,
    init?: RequestInit,
): Promise<T> {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        headers: {
            "Content-Type": "application/json",
            ...init?.headers,
        },
        ...init,
    });

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
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        headers: {
            "Content-Type": "application/json",
            ...init?.headers,
        },
        ...init,
    });

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
    get: <T>(url: string) =>
        request<T>(url),

    post: <T>(url: string, body?: unknown) =>
        request<T>(url, {
            method: "POST",
            body: body ? JSON.stringify(body) : undefined,
        }),

    postBlob: (url: string, body?: unknown) =>
        requestBlob(url, {
            method: "POST",
            body: body ? JSON.stringify(body) : undefined,
        }),

    put: <T>(url: string, body?: unknown) =>
        request<T>(url, {
            method: "PUT",
            body: body ? JSON.stringify(body) : undefined,
        }),

    patch: <T>(url: string, body?: unknown) =>
        request<T>(url, {
            method: "PATCH",
            body: body ? JSON.stringify(body) : undefined,
        }),

    delete: <T>(url: string) =>
        request<T>(url, {
            method: "DELETE",
        }),
};