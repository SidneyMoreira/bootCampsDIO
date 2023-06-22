import createConnection from '../database'
import { UpdateUserService } from "./updateUserService";
import { FakeData } from "../utils/fakeData/fakeData";
import { getConnection } from 'typeorm';

describe('UpdateUserService', () =>{
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

    it('Deve editar o nome do usuário',async () => {
        const mockUser = await fakeData.createUser();

        const updateUserService = new UpdateUserService();

        const result = await updateUserService.execute({
            id: mockUser.id,
            nome: 'Atualiza o Pelego'
        });

        expect(result).toHaveLength(0);
    })
})