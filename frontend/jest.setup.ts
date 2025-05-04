import "@testing-library/jest-dom";

Object.defineProperty(global, 'import', {
    value: {
        meta: {
            env: {
                VITE_BACKEND_URL: 'http://localhost:9000',
            },
        },
    },
});

global.structuredClone = jest.fn((val): unknown =>
    JSON.parse(JSON.stringify(val)),
);