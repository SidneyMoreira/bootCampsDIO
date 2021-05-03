module.exports = server => {

    const connection = require('../database/connection');
    
    server.get('/', (require, response) => {
        response.json({message: 'Bem vindo a nossa Loja de Games'})
    })

    server.get('/pedidos', (require, response) => {
        const sql = 'SELECT pe.id_pedido, clie.nome_cliente, '+
                            'pe.qtd, ga.nome_game, ga.preco, '+
                            'ca.nome_categoria '+
                       'FROM `game-shop`.`pedidos` pe '+
                       'JOIN `game-shop`.`clientes` clie '+
                         'ON pe.fk_idcliente = clie.id_cliente '+
                       'JOIN `game-shop`.`games` ga '+
                         'ON pe.fk_idgames = ga.id_game '+
                       'JOIN `game-shop`.`categorias` ca '+
                         'ON ga.fk_idcategoria = ca.id_categoria';
        connection.query(sql, (error, res) => {
            if (error){
                return error;
            } console.log("pedidos: ", res);
            response.json(res)
        })
    })
};
