import './App.css';
import {useState} from "react";
import {ChakraProvider} from "@chakra-ui/react";
import defaultSystem from "./system/system.ts";
import UserTable from "./components/user/UserTable.tsx";

function App() {
    const [users] = useState(
        [
            {id: 1, email: "rhaneyra@email.com", active: false},
            {id: 2, email: "ageon@email.com", active: true},
            {id: 3, email: "denerys@email.com", active: false}
        ]
    );

    return (
        <ChakraProvider value={defaultSystem}>
            <h1>Users</h1>
            <UserTable users={users}/>
        </ChakraProvider>
    )
}

export default App
