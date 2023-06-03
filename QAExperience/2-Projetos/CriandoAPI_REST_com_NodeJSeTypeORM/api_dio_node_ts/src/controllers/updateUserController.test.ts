import { getConnection } from "typeorm";
import createConnection from "../database";
import { makeMockResponse } from "../utils/mocks/mockResponse";
import { Request } from "express";
import { FakeData } from "../utils/fakeData/fakeData";
import { UpdateUserController } from "./updateUserController";


describe("UpdateUserController", () => {
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

  it('Deve retornar status 204 quando usuario for editado',async () => {
    const updateUserController = new UpdateUserController();
    const mockUser = await fakeData.createUser();
    const request = {
      body: {
        id: mockUser.id,
        nome: "Valida Upate Pelego",
        email: "validaupdatepelego@pelego.com",
      },
    } as Request;

    const response = makeMockResponse();

    await updateUserController.handle(request, response);

    expect(response.state.status).toBe(204);
  })

  it("Deve retornar status 400 quando o nome não for informado", async () => {
    const updateUserController = new UpdateUserController();
    const mockUser = await fakeData.createUser();

    const request = {
      body: {
        id: mockUser.id,
        nome: "",
        email: "validaupdatepelego@pelego.com",
      },
    } as Request;
    const response = makeMockResponse();

    await updateUserController.handle(request, response);

    expect(response.state.status).toBe(400);
  });

  it("Deve retornar status 204 quando o Id não informado", async () => {
    const updateUserController = new UpdateUserController();
    const mockUser = await fakeData.createUser();

    const request = {
      body: {
        id: "",
        nome: "Valida Upate Pelego",
        email: "validaupdatepelego@pelego.com",
      },
    } as Request;
    const response = makeMockResponse();

    await updateUserController.handle(request, response);

    expect(response.state.status).toBe(204);
  });

});
