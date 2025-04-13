import {Button} from "@chakra-ui/react";
import {Link} from "@tanstack/react-router";
import {useDispatch, useSelector} from "react-redux";
import {logout} from "../../features/authenticationSlice.ts";
import React from "react";

const AuthenticationButton = () => {
    const token = useSelector((state : any) => state.authentication.token);
    const dispatch = useDispatch();

    const logoutButtonHandle = (e : React.MouseEvent) => {
        e.preventDefault();
        dispatch(logout());
    };

    if (token) {
        return <Button onClick={logoutButtonHandle}>Logout</Button>;
    }

    return <Link to="/users/login">
        <Button>Login</Button>
    </Link>;
};

export default AuthenticationButton;