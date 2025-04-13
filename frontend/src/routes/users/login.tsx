import {createFileRoute} from "@tanstack/react-router";
import UserLoginForm from "../../components/user/UserLoginForm.tsx";

export const Route = createFileRoute('/users/login')({
    component: UserLoginForm,
});