import axios from "axios";
import gitApi from "./github";

jest.mock('axios')

const axisMock = axios as jest.Mocked<typeof axios>
describe('github', () => {
    it('Deve retornar o Login e o ID do usuário', async () => {

        axisMock.get = jest.fn().mockResolvedValue({data: {
            login: 'sidnei',
            id: '1234566'
        }})
        
        const response = await gitApi.getUser('sidnei')
        expect(response).toMatchObject({
            login: 'sidnei',
            id: '1234566'
        })
    })

    it('Deve retornar a mensagem de usuário não encontrado', async () => {
        axisMock.get = jest.fn().mockResolvedValue({ data: {
            message: 'Not found'
        }})

        const response = await gitApi.getUser('usuario-invalido')
        expect(response).toMatchObject({message: 'Not found'})
    })
})