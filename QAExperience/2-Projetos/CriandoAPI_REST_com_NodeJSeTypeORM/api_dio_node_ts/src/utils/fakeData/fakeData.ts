import { v4 as uuid } from "uuid";
import { CreateUserService } from "../../services/createUserService";

class FakeData {
  createUserService = new CreateUserService();

  async execute() {
    await this.createUserService.execute({
      id: uuid(),
      nome: "Pelego Racional",
      email: "pelego@pelo.com",
    });

    await this.createUserService.execute({
      id: uuid(),
      nome: "Outro Pelego Racional",
      email: "outropelego@pelo.com",
    });
  }

  async createUser() {
    const usuario = await this.createUserService.execute({
      id: uuid(),
      nome: "Update Pelego Racional",
      email: "pelegoupdate@pelego.com",
    });

    return usuario
  }
}

export { FakeData };
