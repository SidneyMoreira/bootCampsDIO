import { screen, render } from "@testing-library/react";
import Perfil from "./Perfil";
import { BrowserRouter } from "react-router-dom";

const mockHistoryPush = jest.fn()
let mockUser = ''

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockHistoryPush,
    useParams: () => ({
        user: mockUser
    })
}))

describe('Perfil', () => {
    render(
        <BrowserRouter>
            <Perfil />
        </BrowserRouter>

    )

    it('Deve rederizar a tabela na pagina caso o usuário seja válido', () => {
        mockUser = 'sidnei'
        expect(screen.getByRole('table')).toBeInTheDocument()
        expect(mockHistoryPush).not.toHaveBeenCalled()
    })

}) 