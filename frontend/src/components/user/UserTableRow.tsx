import React, {useState} from "react";
import {Table} from "@chakra-ui/react";
import {User} from "../../types/User.ts";

interface UserProps {
    data: User
}

const UserTableRow = ({data}: UserProps) => {
    const [activeState, setActiveState] = useState<boolean>(data.active || false);

    const onButtonClickHandle = (e: React.MouseEvent<HTMLElement>) => {
        e.preventDefault();
        setActiveState(!activeState);
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