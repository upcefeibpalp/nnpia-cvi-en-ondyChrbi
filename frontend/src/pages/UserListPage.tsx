import '../App.css';
import {useEffect, useState} from "react";
import UserTable from "../components/user/UserTable.tsx";
import axios from "axios";

function UserListPage() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get(`${import.meta.env.VITE_BACKEND_URL}/api/v1/users`);
            const users = response.data;
            console.debug(users);

            setUsers(users);
        }

        fetchData();
    }, [setUsers]);

    return (
        <>
            <h1>Users</h1>
            <UserTable users={users}/>
        </>
    )
}

export default UserListPage
