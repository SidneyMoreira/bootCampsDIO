import { Router, Response, Request } from "express";
import { CreateUserController } from "./controllers/createUserController";
import { GetAllUseController } from "./controllers/getAllUserController";
import { UpdateUserController } from "./controllers/updateUserController";
import { DeleteUserController } from "./controllers/deleteUserController";

const router = Router();
const createUserController = new CreateUserController();
const getAllUserController = new GetAllUseController();
const updateUserController = new UpdateUserController();
const deleteUserController = new UpdateUserController();

router.get("/", (request: Request, response: Response) => {
  return response.json({ mensagem: "Bem vindo a API" });
});

router.get("/usuarios", getAllUserController.handle)
router.post("/usuarios", createUserController.handle)
router.patch("/usuario", updateUserController.handle);
router.delete("/usuario/:id", deleteUserController.handle);

export { router };