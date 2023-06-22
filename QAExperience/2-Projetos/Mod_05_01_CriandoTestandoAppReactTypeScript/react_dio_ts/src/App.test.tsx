import { fireEvent, render, screen } from '@testing-library/react';
import gitApi from './api/github';
import App from './App';
import { BrowserRouter } from 'react-router-dom';


describe('App', () => {
  let user = ''
  it('Deve receber os dados na resposta, caso o usuário exista', async() => {
    user = 'SidneyMoreira'
    render(
          <App/>   
    )

    jest.spyOn(gitApi, 'getUser')

    const inputUser = screen.getByRole('textbox', {name: 'User'})
        const btnEntrar = screen.getByRole('button',{name: 'Entrar'})
        fireEvent.change(inputUser, {
            target: { value: user}
        })

        fireEvent.click(btnEntrar)

        expect(gitApi.getUser).toHaveBeenCalled()
        const response = await gitApi.getUser(user)
        expect(response).toHaveProperty('login')
  })
  
  it('Não deve receber dados na resposta, caso o usuário não exista', async () => {
    user = 'SineyMorreia'
    render(
        <App/>    
    )

    jest.spyOn(gitApi, 'getUser')

    const inputUser = screen.getByRole('textbox', {name: 'User'})
        const btnEntrar = screen.getByRole('button',{name: 'Entrar'})
        fireEvent.change(inputUser, {
            target: { value: user}
        })

        fireEvent.click(btnEntrar)
        const response = await gitApi.getUser(user)
        expect(response).toMatchObject({message: 'Not found'})
  })
});