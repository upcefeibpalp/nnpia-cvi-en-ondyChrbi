import { render, screen } from '@testing-library/react';
import UserListPage from "@/pages/UserListPage.tsx";
import defaultSystem from "@/system/system.ts";
import {ChakraProvider} from "@chakra-ui/react";
import axios from "axios";

jest.mock('axios');

describe('UserListPage', () => {
    it('Title will be rendered', () => {
        render(<ChakraProvider value={defaultSystem}><UserListPage /></ChakraProvider>);
        expect(screen.getByText('Users')).toBeInTheDocument();
    });

    it('Fetches and displays users', async () => {
        axios.get.mockImplementationOnce(() => Promise.resolve({
            data: [{id: 1, email: "admin@upce.cz", password: "ABC123", active: true}]
        }));

        render(<ChakraProvider value={defaultSystem}><UserListPage /></ChakraProvider>);

        const userTableBody = screen.getByTestId('user-table').getElementsByTagName('tbody');
        expect(userTableBody).toHaveLength(1);

        console.log(userTableBody[0].getElementsByTagName('tr'));

        
    });
});