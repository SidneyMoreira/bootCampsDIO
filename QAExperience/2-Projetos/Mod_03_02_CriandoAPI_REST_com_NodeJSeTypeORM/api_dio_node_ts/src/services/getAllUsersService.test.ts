import { getConnection } from "typeorm";
import createConnection from "../database";
import { GetAllUserService } from "./getAllUsersService";
import { FakeData } from "../utils/fakeData/fakeData";

describe("GetAllUserService", () => {
  beforeAll(async () => {
    const conn = await createConnection();
    await conn.runMigrations();
  });

  afterAll(async () => {
    const conn = getConnection();
    await conn.query("DELETE FROM usuarios");
    await conn.close();
  });

  const fakeData = new FakeData();

  it("Deve retornar todos os usuários cadastrados", async () => {

    await fakeData.execute();
    
    const expectResponse = [
      {
        nome: "Pelego Racional",
        email: "pelego@pelo.com",
      },
      {
        nome: "Outro Pelego Racional",
        email: "outropelego@pelo.com",
      },
    ];

    const getAllUsersService = new GetAllUserService();
    const result = await getAllUsersService.execute();

    expect(result).toMatchObject(expectResponse);
  });
});
