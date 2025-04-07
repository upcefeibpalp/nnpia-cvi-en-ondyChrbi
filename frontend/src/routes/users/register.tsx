import {createFileRoute} from "@tanstack/react-router";
import AddUserPage from "../../pages/AddUserPage.tsx";

export const Route = createFileRoute('/users/register')({
    component: AddUserPage,
});