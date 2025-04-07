import { createFileRoute } from '@tanstack/react-router'
import UserListPage from "../pages/UserListPage.tsx";

export const Route = createFileRoute('/')({
    component: UserListPage,
});