import { getConnection } from "typeorm";
import createConnection from "../database";
import { Request } from "express";
import { makeMockResponse } from "../utils/mocks/mockResponse";
import { CreateUserController } from "./createUserController";


describe("CreateUserController", () => {
  beforeAll(async () => {
    const conn = await createConnection();
    await conn.runMigrations();
  });

  afterAll(async () => {
    const conn = getConnection();
    await conn.query("DELETE FROM usuarios");
    await conn.close();
  });

  const createUserController = new CreateUserController();
  const response = makeMockResponse();

  it("Deve retorna status 201 quando usuário for criado", async () => {
    const request = {
      body: {
        nome: "Teste de alguma coisa",
        email: "notesteno@tesnono.com",
      },
    } as Request;

    expect(response.state.status).toBe(201);

  });

  it("Deve retornar status 400 quando o nome não for informado", async () => {
    const request = {
      body: {
        nome: "",
        email: "notesteno@tesnono.com",
      },
    } as Request;

    await createUserController.handle(request, response);

    expect(response.state.status).toBe(400);
  });

  it("Deve retornar status 201 quando o email não for informado", async () => {
    const request = {
      body: {
        nome: "Pedro de Lara",
        email: "",
      },
    } as Request;

    await createUserController.handle(request, response);

    expect(response.state.status).toBe(201);
  });
});
