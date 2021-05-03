import { useEffect, useState } from 'react';

const GameList = () => {

    const [games, setGames] = useState([])
    
    useEffect(async () => {
        const url = "http://localhost:5000/pedidos";
        const res = await fetch(url);
        setGames(await res.json());
    }, [])
    
    return(
            <table className="striped">
                <thead>
                <tr>
                    <th>Nome do Cliente</th>
                    <th>Produto</th>
                    <th>Categoria</th>
                    <th>Quantidade</th>
                    <th>Preço Total</th>
                </tr>
                </thead>

                <tbody>
                {games.map(game => {
                    let totalPrice = (game.preco * game.qtd).toFixed(2)

                    return(
                        <tr key={game.id_pedido}>
                            <td>{game.nome_cliente}</td>
                            <td>{game.nome_game}</td>
                            <td>{game.nome_categoria}</td>
                            <td>{game.qtd}</td>
                            <td>R$ {totalPrice}</td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
    )
}

export default GameList;
