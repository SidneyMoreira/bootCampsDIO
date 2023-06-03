import { Request, Response } from "express"
import { GetAllUserService } from "../services/getAllUsersService"

class GetAllUseController {
    async handle(request: Request, response: Response){
        const getAllUsersService = new GetAllUserService();

        const users = await getAllUsersService.execute();

        return response.status(200).json(users);

    }
}

export { GetAllUseController }