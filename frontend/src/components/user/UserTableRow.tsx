import React, {useState} from "react";
import {Table} from "@chakra-ui/react";
import {User} from "../../types/User.ts";
import axios from "axios";

interface UserProps {
    data: User
}

const UserTableRow = ({data}: UserProps) => {
    const [activeState, setActiveState] = useState<boolean>(data.active || false);

    const changeActiveState = async (userId: number, activate: boolean) => {
        const response = await axios.post(`${import.meta.env.VITE_BACKEND_URL}/api/v1/users/${userId}/${(activate) ? "activate" : "deactivate"}`);

        if (response.status === 204) {
            setActiveState(activate);
        } else {
            console.error("Failed to activate user");
        }
    }


    const onButtonClickHandle = async (e: React.MouseEvent<HTMLElement>) => {
        e.preventDefault();
        await changeActiveState(data.id, !activeState);
    }

    return <Table.Row key={data.id}>
        <Table.Cell color="black">{data.id}</Table.Cell>
        <Table.Cell color="black">{data.email}</Table.Cell>
        <Table.Cell color="black">{activeToText(activeState)}</Table.Cell>
        <Table.Cell color="white" textAlign="end">
            <button onClick={onButtonClickHandle}>{activeToButtonText(activeState)}</button>
        </Table.Cell>
    </Table.Row>
};


const activeToText = (active?: boolean) => (active) ? "Aktivní" : "Zablokován";

const activeToButtonText = (active?: boolean) => (!active) ? "Aktivovat" : "Zablkovat";

export default UserTableRow;