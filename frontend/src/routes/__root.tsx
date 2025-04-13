import { createRootRoute, Link, Outlet } from '@tanstack/react-router'
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools'
import AuthenticationButton from "../components/authentication/AuthenticationButton.tsx";

export const Route = createRootRoute({
    component: () => (
        <>
            <div className="p-2 flex gap-2">
                <Link to="/">
                    Home
                </Link>
                <Link to="/users">
                    Users
                </Link>
                <AuthenticationButton />
            </div>
            <hr />
            <Outlet />
            <TanStackRouterDevtools />
        </>
    ),
})