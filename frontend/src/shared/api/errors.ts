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
