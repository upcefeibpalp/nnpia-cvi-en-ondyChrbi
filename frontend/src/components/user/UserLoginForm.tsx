import {Button, Field, Heading, Input, Stack} from "@chakra-ui/react";
import * as yup from "yup";
import {useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import axios from "axios";
import {useDispatch} from "react-redux";
import {login} from "../../features/authenticationSlice.ts";
import {useNavigate} from "@tanstack/react-router";

const validationSchema = yup.object().shape({
    email: yup.string().email("Invalid email format").required("Email is required"),
    password: yup.string().min(4, "Password must be at least 8 characters").required("Password is required"),
})

interface FormValues {
    email: string
    password: string
}

const UserLoginForm = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<FormValues>({
        resolver: yupResolver(validationSchema)
    });

    const onSubmit = handleSubmit(async (data) => {
        console.log(data);
        const response = await axios.post(`${import.meta.env.VITE_BACKEND_URL}/api/v1/auth/login`, data);

        console.log(response.data);
        dispatch(login(response.data.token));
        navigate({ to: '/'});
    });

    return (
        <form onSubmit={onSubmit}>
            <Stack gap="4" align="flex-start" maxW="sm">
                <Heading as="h2">User login</Heading>
                <Field.Root invalid={!!errors.email}>
                    <Field.Label>Email</Field.Label>
                    <Input type="email" {...register("email")} />
                    <Field.ErrorText>{errors.email?.message}</Field.ErrorText>
                </Field.Root>

                <Field.Root invalid={!!errors.password}>
                    <Field.Label>Password</Field.Label>
                    <Input type="password" {...register("password")} />
                    <Field.ErrorText>{errors.password?.message}</Field.ErrorText>
                </Field.Root>

                <Button type="submit">Submit</Button>
            </Stack>
        </form>
    );
};

export default UserLoginForm;