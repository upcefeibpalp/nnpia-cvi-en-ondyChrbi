import { StrictMode } from 'react'
import ReactDOM from 'react-dom/client'
import { RouterProvider, createRouter } from '@tanstack/react-router'
import { routeTree } from './routeTree.gen'
import defaultSystem from "./system/system.ts";
import {ChakraProvider} from "@chakra-ui/react";
import { store } from './app/store'
import { Provider } from 'react-redux'

const router = createRouter({ routeTree })

declare module '@tanstack/react-router' {
    interface Register {
        router: typeof router
    }
}

// Render the app
const rootElement = document.getElementById('root')!
if (!rootElement.innerHTML) {
    const root = ReactDOM.createRoot(rootElement)
    root.render(
        <StrictMode>
            <Provider store={store}>
                <ChakraProvider value={defaultSystem}>
                    <RouterProvider router={router} />
                </ChakraProvider>
            </Provider>
        </StrictMode>,
    )
}