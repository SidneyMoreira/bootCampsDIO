import { Request, Response } from 'express'
import { v4 as uuid } from 'uuid';
import { CreateUserService } from '../services/createUserService';

class CreateUserController{
    async handle(request: Request, response: Response) {

        const createUserService = new CreateUserService();

        const id = uuid();
        const nome = request.body.nome;
        const email = request.body.email;

        if(nome.length === 0 ){
            return response.status(400).json({mensagem: 'O campo Nome não pode se em branco'})
        }
        
        const user = await createUserService.execute({id,nome, email});
        return response.status(201).json(user)
    }
}

export{ CreateUserController}