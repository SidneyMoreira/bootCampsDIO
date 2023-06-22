import { getConnection } from "typeorm";
import createConnection from "../database";
import { v4 as uuid } from "uuid";
import { CreateUserService } from "./createUserService";

describe("CreateUserService", () => {
  beforeAll(async () => {
    const conn = await createConnection();
    await conn.runMigrations();
  });

  afterAll(async () => {
    const conn = getConnection();
    await conn.query("DELETE FROM usuarios");
    await conn.close();
  });

  it("Deve retornar o id do usuário criado", async () => {
    const createUserService = new CreateUserService();

    const result = await createUserService.execute({
      id: uuid(),
      nome: "Fala Algo",
      email: "",
    });

    expect(result).toHaveProperty('id');
  });
});
