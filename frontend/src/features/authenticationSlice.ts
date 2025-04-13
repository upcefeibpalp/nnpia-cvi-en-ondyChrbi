import { createSlice, PayloadAction } from '@reduxjs/toolkit'

export interface AuthenticationState {
    token: string | null | undefined
}

const initialState: AuthenticationState = {
    token: localStorage.getItem("token") || null,
}

export const authenticationSlice = createSlice({
    name: 'authentication',
    initialState,
    reducers: {
        login: (state, action: PayloadAction<string>) => {
            state.token = action.payload;
            localStorage.setItem("token", action.payload);
        },
        logout: (state) => {
            state.token = null;
            localStorage.removeItem("token")
        },
    },
})

// Action creators are generated for each case reducer function
export const { login, logout } = authenticationSlice.actions

export default authenticationSlice.reducer