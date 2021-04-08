select * from Produtos;

insert into Produtos (Nome, Descricao, Preco) values ('Caneta', 'Caneta Azul', 1.5),
('Caneta', 'Caneta Vermelha', 1.5),('Borracha', 'Borracha Branca', 3.5),('Lapis', 'Lapis FaberCastell H2B', 5.0),
('Caderno', 'Caderno Universitário Vingadores', 20.00);

select * from Pedido;

insert Pedido
 values (GETDATE(), 0,3.00, 1,1);

insert Pedido
 values (GETDATE(), 0,20.00, 2,2);

select * from PedidoItem;

insert PedidoItem
 values (1,1, 1.5,1),(1,2, 1.5,1);

 insert PedidoItem
 values (2,5, 20,1);

 select * from clientes;
 select * from produtos;
 select * from pedido;
 select * from pedidoitem;

 insert into Clientes values ('Roseli','F',getdate()),('Sidney','J',getdate())

select * from Status;


select * from PedidoItemLog;

select *
  from clientes cli
  inner join pedido ped
  on cli.codigo = ped.codigocliente

  select *
  from clientes cli
  left join pedido ped
  on cli.codigo = ped.codigocliente
  where ped.totalpedido > 10

  select *
  from pedido ped 
  right join clientes cli
  on cli.codigo = ped.codigocliente
  where ped.totalpedido > 10;

  select t4.codigo,
		 t4.descricao,
		 sum(t1.preco * t1.quantidade) TotalItem
    from pedidoitem t1
	inner join pedidoitemlog t2
	on t1.codigopedido = t2.codigopedido
	and t1.codigoproduto = t2.codigoproduto
	inner join statuspedidoitem t3
	on t3.codigo = t2.codigostatuspedidoitem
	inner join produtos t4
	on t4.codigo = t2.codigoproduto
group by t4.codigo, t4.descricao