import { configureStore } from '@reduxjs/toolkit'
import authenticationRedurec from '../features/authenticationSlice'

export const store = configureStore({
    reducer: {
        authentication : authenticationRedurec
    },
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch