import './App.css';
import {useEffect, useState} from "react";
import {ChakraProvider} from "@chakra-ui/react";
import defaultSystem from "./system/system.ts";
import UserTable from "./components/user/UserTable.tsx";
import axios from "axios";

function App() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get('http://localhost:9000/api/v1/users');
            const users = response.data;
            console.debug(users);

            setUsers(users);
        }

        fetchData();
    }, [setUsers]);

    return (
        <ChakraProvider value={defaultSystem}>
            <h1>Users</h1>
            <UserTable users={users}/>
        </ChakraProvider>
    )
}

export default App
