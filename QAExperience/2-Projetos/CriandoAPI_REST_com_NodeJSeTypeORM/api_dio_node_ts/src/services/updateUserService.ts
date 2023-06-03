import { getRepository } from "typeorm";
import { Usuario } from "../entities/Usuario";

interface IUsuario {
    id: string;
  nome: string;
  email?: string;
}

class UpdateUserService {
  async execute({ id, nome, email }: IUsuario) {
    const usuario = await getRepository(Usuario)
      .createQueryBuilder()
      .update(Usuario)
      .set(
        {
          nome: nome,
          email: email,
        }
      )
      .where("id = :id", { id })
      .execute();
    console.log(usuario)
    return usuario.raw;
  }
}

export { UpdateUserService };

