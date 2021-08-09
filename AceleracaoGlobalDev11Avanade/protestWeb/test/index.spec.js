const service = require('../controllers/protest/index');

describe('protest file test', () => {

    describe('create method test', () => {
        test('return sucess when creat a post', () => {
            const result = service.create('cascao', 'mockMessage')
            expect(result.status).toEqual(200)
            expect(result.message).toEqual('Protest created')
        });

        test('return a error when try create a post', () => {
            const result = service.create()
            expect(result.status).toEqual(400)
            expect(result.message).toEqual('Protest not created')
            expect(result).not.toBeUndefined()
        });
    });
    
});
