import { StrictMode } from 'react'
import ReactDOM from 'react-dom/client'
import { RouterProvider, createRouter } from '@tanstack/react-router'
import { routeTree } from './routeTree.gen'
import defaultSystem from "./system/system.ts";
import {ChakraProvider} from "@chakra-ui/react";

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
            <ChakraProvider value={defaultSystem}>
                <RouterProvider router={router} />
            </ChakraProvider>
        </StrictMode>,
    )
}