export class ApiError extends Error {
    public readonly status: number;
    public readonly data: unknown;

    constructor(
        status: number,
        data: unknown,
    ) {
        super(`Request failed with status ${status}`);

        this.status = status;
        this.data = data;
    }
}

export class TimeoutError extends Error {
    constructor(ms: number) {
        super(`Request timed out after ${ms}ms`);
        this.name = 'TimeoutError';
    }
}
