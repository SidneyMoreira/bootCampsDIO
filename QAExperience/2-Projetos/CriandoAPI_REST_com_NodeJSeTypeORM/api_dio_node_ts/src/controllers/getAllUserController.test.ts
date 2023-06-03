import { getConnection } from "typeorm";
import createConnection from "../database";
import { FakeData } from "../utils/fakeData/fakeData";
import { makeMockRequest } from "../utils/mocks/mockRequest";
import { makeMockResponse } from "../utils/mocks/mockResponse";
import { GetAllUseController } from "./getAllUserController";
import { Request } from "express";

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

  const fakeData = new FakeData();
  

  it('Deve retorna status 200 quando pegar todos os usuários',async () => {
    await fakeData.execute()
    const getAllUserController = new GetAllUseController();
    const request = makeMockRequest({})
    const response = makeMockResponse();

    await getAllUserController.handle(request,response);
    expect(response.state.status).toBe(200)
  })
})