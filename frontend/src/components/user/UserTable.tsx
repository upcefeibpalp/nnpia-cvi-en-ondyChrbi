import {Table} from "@chakra-ui/react";
import UserTableRow from "./UserTableRow.tsx";
import {User} from "../../types/User.ts";

interface UserTableProps {
    users: User[]
}

const UserTable = ({users} : UserTableProps) => {
    return (
        <Table.Root bg={{base: "dark"}} data-testid="user-table">
            <Table.Header>
                <Table.Row>
                    <Table.ColumnHeader>ID</Table.ColumnHeader>
                    <Table.ColumnHeader>Email</Table.ColumnHeader>
                    <Table.ColumnHeader>Aktivní</Table.ColumnHeader>
                    <Table.ColumnHeader>Akce</Table.ColumnHeader>
                </Table.Row>
            </Table.Header>
            <Table.Body>
                {users.map((user) => {
                    return <UserTableRow key={user.id} data={user} />
                })}
            </Table.Body>
        </Table.Root>
    )
}

export default UserTable;