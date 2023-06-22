import { screen, render, fireEvent } from "@testing-library/react";
import Home from "./Home";
import { BrowserRouter } from "react-router-dom";
import gitApi from "../api/github";

const mockHistoryPush = jest.fn()

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockHistoryPush
}))

describe('Home', () => {
    it('Deve informar o usuário e ser redirecionado a pagina de perfil', async() =>{
        gitApi.getUser = jest.fn().mockResolvedValue({login: 'SidneyMoreira'})
        const user = 'SidneyMoreira'
        render(
            <BrowserRouter>
                <Home />
            </BrowserRouter>
        )
        
        const inputUser = screen.getByRole('textbox', {name: 'User'})
        const btnEntrar = screen.getByRole('button',{name: 'Entrar'})
        fireEvent.change(inputUser, {
            target: { value: user}
        })

        fireEvent.click(btnEntrar)

        expect(gitApi.getUser).toHaveBeenCalledWith(user)
    })

    it('Não deve redirecionar para a página de perfil, caso o usuário não seja informado', () => {

        window.alert = jest.fn()
        render(
            <BrowserRouter>
                <Home />
            </BrowserRouter>
        )
    
        const btnEntrar = screen.getByRole('button',{name: 'Entrar'})

        fireEvent.click(btnEntrar)

        expect(mockHistoryPush).not.toHaveBeenCalled()
        expect(window.alert).toHaveBeenCalled()

    })

})